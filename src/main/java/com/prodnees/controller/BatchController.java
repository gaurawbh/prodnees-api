package com.prodnees.controller;

import com.prodnees.action.BatchAction;
import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.action.rel.DocumentRightAction;
import com.prodnees.action.stage.EventAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.batch.BatchApprovalDocument;
import com.prodnees.domain.enums.ApprovalDocumentState;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.Associates;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.dto.batch.BatchApprovalDocumentDto;
import com.prodnees.dto.batch.BatchDto;
import com.prodnees.dto.batch.BatchRightDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.batch.BatchModel;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.service.rels.BatchProductApprovalDocumentService;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.util.ValidatorUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static com.prodnees.config.constants.APIErrors.ACCESS_DENIED;
import static com.prodnees.config.constants.APIErrors.BATCH_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.UPDATE_DENIED;
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class BatchController {
    private final RequestValidator requestValidator;
    private final BatchAction batchAction;
    private final BatchRightAction batchRightAction;
    private final StageAction stageAction;
    private final EventAction eventAction;
    private final AssociatesService associatesService;
    private final DocumentRightAction documentRightAction;
    private final BatchProductApprovalDocumentService batchProductApprovalDocumentService;

    public BatchController(RequestValidator requestValidator,
                           BatchAction batchAction,
                           BatchRightAction batchRightAction,
                           StageAction stageAction,
                           EventAction eventAction,
                           AssociatesService associatesService,
                           DocumentRightAction documentRightAction,
                           BatchProductApprovalDocumentService batchProductApprovalDocumentService) {
        this.requestValidator = requestValidator;
        this.batchAction = batchAction;
        this.batchRightAction = batchRightAction;
        this.stageAction = stageAction;
        this.eventAction = eventAction;
        this.associatesService = associatesService;
        this.documentRightAction = documentRightAction;
        this.batchProductApprovalDocumentService = batchProductApprovalDocumentService;
    }

    /**
     * On saving a new BatchProduct:
     * <p>save to BatchProductRights</p>
     *
     * @param dto
     * @param servletRequest
     * @return
     */

    @PostMapping("/batch")
    public ResponseEntity<?> saveBatch(@Validated @RequestBody BatchDto dto,
                                       HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        dto.setId(0);
        Batch batch = MapperUtil.getDozer().map(dto, Batch.class);
        batch.setCreatedDate(LocalDate.now()).setState(BatchState.OPEN);
        BatchModel batchModel = batchAction.save(batch);
        batchRightAction.save(new BatchRight()
                .setUserId(userId)
                .setBatchId(batchModel.getId())
                .setObjectRightsType(ObjectRight.OWNER));
        return configure(batchModel);
    }

    /**
     * returns {@link BatchModel} by it's id if id is provided
     * <p>returns list of {@link BatchModel}</p> that belongs to a user if id is not provided
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @GetMapping("/batches")
    public ResponseEntity<?> getBatches(@RequestParam Optional<Integer> id,
                                        HttpServletRequest servletRequest) {
        int ownerId = requestValidator.extractUserId();
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Optional<BatchRight> batchProductRights = batchRightAction.findByBatchIdAndUserId(integer, ownerId);
            Assert.isTrue(batchProductRights.isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(batchAction.getModelById(batchProductRights.get().getBatchId()));
        }, () -> {
            Iterable<Integer> batchIds = batchRightAction.getAllByUserId(ownerId)
                    .stream()
                    .map(BatchRight::getBatchId)
                    .collect(Collectors.toList());
            atomicReference.set(batchAction.getListModelByIds(batchIds));
        });
        return configure(atomicReference.get());
    }

    /**
     * @param state          of {@link Batch}
     * @param servletRequest
     * @return list of {@link BatchModel} that belongs to the User and by {@link BatchState}
     */
    @GetMapping("/batches/state")
    public ResponseEntity<?> getAllByStatus(@RequestParam BatchState state,
                                            HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        return configure(batchAction.getAllByUserIdAndState(userId, state));
    }

    /**
     * Update {@link Batch} -> {@link BatchState}
     *
     * @param state          of {@link Batch}
     * @param id             of {@link Batch}
     * @param servletRequest
     * @return
     */
    @PutMapping("/batches/state")
    public ResponseEntity<?> updateStatus(@RequestParam BatchState state,
                                          @RequestParam int id,
                                          HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(id, userId), UPDATE_DENIED);
        Batch batch = batchAction.getById(id);
        LocalAssert.isTrue(isValidBatchStateUpdate(batch.getState(), state), "invalid state for the Batch");
        batch.setState(state);
        return LocalResponse.configure(batchAction.save(batch));
    }

    /**
     * Check if the new {@link BatchState} of a  {@link Batch} is valid
     *
     * @param currentState
     * @param newState
     * @return
     */

    boolean isValidBatchStateUpdate(BatchState currentState, BatchState newState) {
        switch (newState) {
            case SUSPENDED:
            case COMPLETE:
                return currentState == BatchState.OPEN
                        || currentState == BatchState.IN_PROGRESS;
            case OPEN:
                return currentState == BatchState.IN_PROGRESS;
            case IN_PROGRESS:
                return currentState == BatchState.COMPLETE
                        || currentState == BatchState.OPEN;
            default:
                return false;
        }
    }


    /**
     * only name and description can be changed of a BatchProduct.
     * <p>productId on Request Body will be ignored</p>
     * <i>User must have editor rights, i.e. {@link ObjectRight#OWNER}  or {@link ObjectRight#EDITOR} </i>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PutMapping("/batch")
    public ResponseEntity<?> updateBatch(@Validated @RequestBody BatchDto dto,
                                         HttpServletRequest servletRequest) {
        int editorId = requestValidator.extractUserId();
        Assert.isTrue(batchAction.existsById(dto.getId()), OBJECT_NOT_FOUND.getMessage());
        Assert.isTrue(batchRightAction.hasBatchEditorRights(dto.getId(), editorId), OBJECT_NOT_FOUND.getMessage());
        Batch batch = batchAction.getById(dto.getId());
        batch.setName(dto.getName())
                .setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), batch.getDescription()));

        return configure(batchAction.save(batch));
    }

    /**
     * <p>check the user has {@link ObjectRight#OWNER} rights of the Batch Product</p>
     * <p>check the Batch Product does not have any States or Events associated with it</p>
     *
     * @param id
     * @return
     */
    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteBatch(@RequestParam int id) {
        int ownerId = requestValidator.extractUserId();
        BatchRight batchRight = batchRightAction.findByBatchIdAndUserId(id, ownerId).orElseThrow(NeesNotFoundException::new);
        Assert.isTrue(batchRight.getObjectRightsType().equals(ObjectRight.OWNER), ACCESS_DENIED.getMessage());
        batchAction.deleteById(id);
        return configure();
    }

    /**
     * <p>Save the {@link BatchRight}</p>
     * <p>Send email to the user for getting the rights</p>
     * <i>Only associates can be given {@link BatchRight}</i>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PutMapping("/batch-right")
    public ResponseEntity<?> saveBatchRight(@Validated @RequestBody BatchRightDto dto,
                                            HttpServletRequest servletRequest) {
        int adminId = requestValidator.extractUserId();
        Assert.isTrue(dto.getObjectRightsType() != ObjectRight.OWNER, "you can only assign an editor or a  viewer");
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(adminId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        Optional<BatchRight> batchProductRightsOpt = batchRightAction.findByBatchIdAndUserId(dto.getBatchId(), adminId);
        Assert.isTrue(batchProductRightsOpt.isPresent() && batchProductRightsOpt.get().getObjectRightsType().equals(ObjectRight.OWNER),
                "only owners can invite others to admin their batch product or update the rights");
        return configure(batchRightAction.save(dto));
    }

    /**
     * if rightOf path is product,
     * <i>return {@link java.util.List} of {@link BatchRight} by batchProductId</i>
     * <p>if rightOf path is user,</p>
     * <i>return {@link java.util.List} of {@link BatchRight} by userId</i>
     *
     * @param rightOf
     * @param batchProductId
     * @param servletRequest
     * @return
     */
    @GetMapping("/batch-right/{rightOf}")
    public ResponseEntity<?> getBatchProductRight(@PathVariable String rightOf,
                                                  @RequestParam Optional<Integer> batchProductId,
                                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        switch (rightOf) {
            case "batch-product":
                LocalAssert.isTrue(batchProductId.isPresent(), "batchProductId must be provided for batch-product rights");
                LocalAssert.isTrue(batchRightAction.hasBatchReaderRights(batchProductId.get(), userId), BATCH_NOT_FOUND);
                return configure(batchRightAction.getAllByBatchId(batchProductId.get()));
            case "user":
                return configure(batchRightAction.getAllModelByUserId(userId));
            default:
                throw new NeesNotFoundException(String.format("no handler found for %s", servletRequest.getRequestURI()), 99);
        }

    }

    /**
     * You must be the owner of the Batch Product
     * <p>The other user must not be the owner of the Batch Product</p>
     *
     * @param batchProductId
     * @param userId
     * @param servletRequest
     * @return
     */
    @DeleteMapping("/batch-right")
    public ResponseEntity<?> deleteBatchRight(@RequestParam int batchProductId, int userId,
                                              HttpServletRequest servletRequest) {
        int adminId = requestValidator.extractUserId();
        Assert.isTrue(userId != adminId, "you cannot delete your own batch product rights");
        Optional<BatchRight> adminBatchProductRightOpt = batchRightAction.findByBatchIdAndUserId(batchProductId, adminId);// check you have permission, you are an owner
        Assert.isTrue(adminBatchProductRightOpt.isPresent() && adminBatchProductRightOpt.get().getObjectRightsType() == ObjectRight.OWNER,
                UPDATE_DENIED.getMessage());
        Optional<BatchRight> userBatchProductRightOpt = batchRightAction.findByBatchIdAndUserId(batchProductId, userId); // check the other user is not the owner
        Assert.isTrue(userBatchProductRightOpt.isPresent() && userBatchProductRightOpt.get().getObjectRightsType() != ObjectRight.OWNER,
                "you cannot remove another owner's batch product rights");
        batchRightAction.deleteByBatchIdAndUserId(batchProductId, userId);
        return configure();
    }

    /**
     * <p>Validate that user and approver are Associates</p>
     * <p>Validate that user has right to Document</p>
     * <p>Validate that user has right to Product</p>
     *
     * @param dto
     * @return
     */
    @PostMapping("/batch/approval-document")
    public ResponseEntity<?> addApprovalDocument(@RequestBody @Validated BatchApprovalDocumentDto dto) {

        int userId = requestValidator.extractUserId();
        Optional<Associates> associatesOptional = associatesService.findByAdminIdAndAssociateEmail(userId, dto.getApproverEmail());
        LocalAssert.isTrue(associatesOptional.isPresent(), "approver must be an associate.");
        LocalAssert.isTrue(documentRightAction.existsByDocumentIdAndUserId(dto.getDocumentId(), userId), "document not found");
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(dto.getBatchId(), userId), OBJECT_NOT_FOUND);
        BatchApprovalDocument batchApprovalDocument = new BatchApprovalDocument()
                .setBatchId(dto.getBatchId())
                .setDocumentId(dto.getDocumentId())
                .setApproverId(associatesOptional.get().getAssociateId())
                .setApproverEmail(associatesOptional.get().getAssociateEmail())
                .setState(ApprovalDocumentState.OPEN);

        batchProductApprovalDocumentService.save(batchApprovalDocument);
        DocumentRight documentRight = new DocumentRight()
                .setUserId(associatesOptional.get().getAssociateId())
                .setDocumentId(dto.getDocumentId())
                .setDocumentRightsType(ObjectRight.EDITOR);
        documentRightAction.save(documentRight);

        return configure();

    }


}
