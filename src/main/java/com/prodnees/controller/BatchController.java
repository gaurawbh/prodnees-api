package com.prodnees.controller;

import com.prodnees.action.BatchAction;
import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.action.rel.DocumentRightAction;
import com.prodnees.action.state.EventAction;
import com.prodnees.action.state.StateAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.batchproduct.BatchProductApprovalDocument;
import com.prodnees.domain.enums.ApprovalDocumentState;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.domain.enums.ObjectRightType;
import com.prodnees.domain.rels.Associates;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.domain.state.State;
import com.prodnees.dto.batch.BatchDto;
import com.prodnees.dto.batch.BatchProductApprovalDocumentDto;
import com.prodnees.dto.batch.BatchRightDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.BatchModel;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static com.prodnees.config.constants.APIErrors.ACCESS_DENIED;
import static com.prodnees.config.constants.APIErrors.BATCH_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.REFERENCED_OBJECT;
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
    private final StateAction stateAction;
    private final EventAction eventAction;
    private final AssociatesService associatesService;
    private final DocumentRightAction documentRightAction;
    private final BatchProductApprovalDocumentService batchProductApprovalDocumentService;

    public BatchController(RequestValidator requestValidator,
                           BatchAction batchAction,
                           BatchRightAction batchRightAction,
                           StateAction stateAction,
                           EventAction eventAction,
                           AssociatesService associatesService,
                           DocumentRightAction documentRightAction,
                           BatchProductApprovalDocumentService batchProductApprovalDocumentService) {
        this.requestValidator = requestValidator;
        this.batchAction = batchAction;
        this.batchRightAction = batchRightAction;
        this.stateAction = stateAction;
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
    public ResponseEntity<?> saveBatchProduct(@Validated @RequestBody BatchDto dto,
                                              HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        dto.setId(0);
        Batch batch = MapperUtil.getDozer().map(dto, Batch.class);
        batch.setCreatedDate(LocalDate.now()).setStatus(BatchStatus.INITIAL);
        BatchModel batchModel = batchAction.save(batch);
        batchRightAction.save(new BatchRight()
                .setUserId(userId)
                .setBatchProductId(batchModel.getId())
                .setObjectRightsType(ObjectRightType.OWNER));
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
    public ResponseEntity<?> getBatchProducts(@RequestParam Optional<Integer> id,
                                              HttpServletRequest servletRequest) {
        int ownerId = requestValidator.extractUserId(servletRequest);
        Iterable<State> stateIterable = new ArrayList<>();
        List<State> stateList = new LinkedList<>();
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Optional<BatchRight> batchProductRights = batchRightAction.findByBatchIdAndUserId(integer, ownerId);
            Assert.isTrue(batchProductRights.isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(batchAction.getModelById(batchProductRights.get().getBatchProductId()));
        }, () -> {
            Iterable<Integer> batchProductIds = batchRightAction.getAllByOwnerId(ownerId)
                    .stream()
                    .map(BatchRight::getBatchProductId)
                    .collect(Collectors.toList());
            atomicReference.set(batchAction.getAllByIds(batchProductIds));
        });
        return configure(atomicReference.get());
    }

    /**
     * @param status         of {@link Batch}
     * @param servletRequest
     * @return list of {@link BatchModel} that belongs to the User and by {@link BatchStatus}
     */
    @GetMapping("/batches/status")
    public ResponseEntity<?> getAllByStatus(@RequestParam BatchStatus status,
                                            HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return configure(batchAction.getAllByUserIdAndStatus(userId, status));
    }

    /**
     * Update {@link Batch} -> {@link BatchStatus}
     *
     * @param status         of {@link Batch}
     * @param id             of {@link Batch}
     * @param servletRequest
     * @return
     */
    @PutMapping("/batches/status")
    public ResponseEntity<?> updateStatus(@RequestParam BatchStatus status,
                                          @RequestParam int id,
                                          HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(id, userId), UPDATE_DENIED);
        Batch batch = batchAction.getById(id);
        LocalAssert.isTrue(isValidBatchProductStatusUpdate(batch.getStatus(), status), "invalid status for the Batch Product");
        batch.setStatus(status);
        return LocalResponse.configure(batchAction.save(batch));
    }

    /**
     * Check if the new {@link BatchStatus} of a  {@link Batch} is valid
     *
     * @param currentStatus
     * @param newStatus
     * @return
     */

    boolean isValidBatchProductStatusUpdate(BatchStatus currentStatus, BatchStatus newStatus) {
        switch (newStatus) {
            case SUSPENDED:
            case COMPLETE:
                return currentStatus == BatchStatus.INITIAL
                        || currentStatus == BatchStatus.IN_PROGRESS;
            case INITIAL:
                return currentStatus == BatchStatus.IN_PROGRESS;
            case IN_PROGRESS:
                return currentStatus == BatchStatus.COMPLETE
                        || currentStatus == BatchStatus.INITIAL;
            default:
                return false;
        }
    }


    /**
     * only name and description can be changed of a BatchProduct.
     * <p>productId on Request Body will be ignored</p>
     * <i>User must have editor rights, i.e. {@link ObjectRightType#OWNER}  or {@link ObjectRightType#EDITOR} </i>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PutMapping("/batch")
    public ResponseEntity<?> updateBatchProduct(@Validated @RequestBody BatchDto dto,
                                                HttpServletRequest servletRequest) {
        int editorId = requestValidator.extractUserId(servletRequest);
        Assert.isTrue(batchAction.existsById(dto.getId()), OBJECT_NOT_FOUND.getMessage());
        Assert.isTrue(batchRightAction.hasBatchEditorRights(dto.getId(), editorId), OBJECT_NOT_FOUND.getMessage());
        Batch batch = batchAction.getById(dto.getId());
        batch.setName(dto.getName())
                .setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), batch.getDescription()));

        return configure(batchAction.save(batch));
    }

    /**
     * <p>check the user has {@link ObjectRightType#OWNER} rights of the Batch Product</p>
     * <p>check the Batch Product does not have any States or Events associated with it</p>
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteBatchProduct(@RequestParam int id,
                                                HttpServletRequest servletRequest) {
        int ownerId = requestValidator.extractUserId(servletRequest);
        Optional<BatchRight> batchProductRightsOptional = batchRightAction.findByBatchIdAndUserId(id, ownerId);
        batchProductRightsOptional.ifPresentOrElse(batchProductRights -> {
            Assert.isTrue(batchProductRights.getObjectRightsType().equals(ObjectRightType.OWNER), ACCESS_DENIED.getMessage());
            Assert.isTrue(!stateAction.existsByBatchId(id), REFERENCED_OBJECT.getMessage());
            Assert.isTrue(eventAction.existsByBatchId(id), REFERENCED_OBJECT.getMessage());
            batchAction.deleteById(id);
        }, () -> {
            throw new NeesNotFoundException();
        });
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
    public ResponseEntity<?> saveBatchProductRight(@Validated @RequestBody BatchRightDto dto,
                                                   HttpServletRequest servletRequest) {
        int adminId = requestValidator.extractUserId(servletRequest);
        Assert.isTrue(dto.getObjectRightsType() != ObjectRightType.OWNER, "you can only assign an editor or a  viewer");
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(adminId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        Optional<BatchRight> batchProductRightsOpt = batchRightAction.findByBatchIdAndUserId(dto.getBatchId(), adminId);
        Assert.isTrue(batchProductRightsOpt.isPresent() && batchProductRightsOpt.get().getObjectRightsType().equals(ObjectRightType.OWNER),
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
        int userId = requestValidator.extractUserId(servletRequest);
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
    public ResponseEntity<?> deleteBatchProductRights(@RequestParam int batchProductId, int userId,
                                                      HttpServletRequest servletRequest) {
        int adminId = requestValidator.extractUserId(servletRequest);
        Assert.isTrue(userId != adminId, "you cannot delete your own batch product rights");
        Optional<BatchRight> adminBatchProductRightOpt = batchRightAction.findByBatchIdAndUserId(batchProductId, adminId);// check you have permission, you are an owner
        Assert.isTrue(adminBatchProductRightOpt.isPresent() && adminBatchProductRightOpt.get().getObjectRightsType() == ObjectRightType.OWNER,
                UPDATE_DENIED.getMessage());
        Optional<BatchRight> userBatchProductRightOpt = batchRightAction.findByBatchIdAndUserId(batchProductId, userId); // check the other user is not the owner
        Assert.isTrue(userBatchProductRightOpt.isPresent() && userBatchProductRightOpt.get().getObjectRightsType() != ObjectRightType.OWNER,
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
     * @param servletRequest
     * @return
     */
    @PostMapping("/batch/approval-document")
    public ResponseEntity<?> addApprovalDocument(@RequestBody @Validated BatchProductApprovalDocumentDto dto,
                                                 HttpServletRequest servletRequest) {

        int userId = requestValidator.extractUserId(servletRequest);
        Optional<Associates> associatesOptional = associatesService.findByAdminIdAndAssociateEmail(userId, dto.getApproverEmail());
        LocalAssert.isTrue(associatesOptional.isPresent(), "approver must be an associate.");
        LocalAssert.isTrue(documentRightAction.existsByDocumentIdAndUserId(dto.getDocumentId(), userId), "document not found");
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(dto.getBatchProductId(), userId), OBJECT_NOT_FOUND);
        BatchProductApprovalDocument batchProductApprovalDocument = new BatchProductApprovalDocument()
                .setBatchProductId(dto.getBatchProductId())
                .setDocumentId(dto.getDocumentId())
                .setApproverId(associatesOptional.get().getAssociateId())
                .setApproverEmail(associatesOptional.get().getAssociateEmail())
                .setState(ApprovalDocumentState.OPEN);

        batchProductApprovalDocumentService.save(batchProductApprovalDocument);
        DocumentRight documentRight = new DocumentRight()
                .setUserId(associatesOptional.get().getAssociateId())
                .setDocumentId(dto.getDocumentId())
                .setDocumentRightsType(ObjectRightType.EDITOR);
        documentRightAction.save(documentRight);

        return configure();

    }


}
