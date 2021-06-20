package com.prodnees.controller;

import com.prodnees.action.BatchAction;
import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.action.rel.DocumentRightAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageTodoAction;
import com.prodnees.auth.filter.RequestContext;
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
import com.prodnees.model.batch.BatchModel;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.service.rels.BatchProductApprovalDocumentService;
import com.prodnees.util.LocalAssert;
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
    private final BatchAction batchAction;
    private final BatchRightAction batchRightAction;
    private final StageAction stageAction;
    private final StageTodoAction stageTodoAction;
    private final AssociatesService associatesService;
    private final DocumentRightAction documentRightAction;
    private final BatchProductApprovalDocumentService batchProductApprovalDocumentService;

    public BatchController(BatchAction batchAction,
                           BatchRightAction batchRightAction,
                           StageAction stageAction,
                           StageTodoAction stageTodoAction,
                           AssociatesService associatesService,
                           DocumentRightAction documentRightAction,
                           BatchProductApprovalDocumentService batchProductApprovalDocumentService) {
        this.batchAction = batchAction;
        this.batchRightAction = batchRightAction;
        this.stageAction = stageAction;
        this.stageTodoAction = stageTodoAction;
        this.associatesService = associatesService;
        this.documentRightAction = documentRightAction;
        this.batchProductApprovalDocumentService = batchProductApprovalDocumentService;
    }

    /**
     * On saving a new BatchProduct:
     * <p>save to BatchProductRights</p>
     *
     * @param dto
     * @return
     */

    @PostMapping("/batch")
    public ResponseEntity<?> saveBatch(@Validated @RequestBody BatchDto dto) {
        return configure(batchAction.create(dto));
    }

    /**
     * returns {@link BatchModel} by it's id if id is provided
     * <p>returns list of {@link BatchModel}</p> that belongs to a user if id is not provided
     *
     * @param id
     * @return
     */
    @GetMapping("/batches")
    public ResponseEntity<?> getBatches(@RequestParam Optional<Integer> id) {
        int ownerId = RequestContext.getUserId();
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            BatchRight batchRights = batchRightAction.getByBatchIdAndUserId(integer, ownerId);
            atomicReference.set(batchAction.getModelById(batchRights.getBatchId()));
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
     * @param state of {@link Batch}
     * @return list of {@link BatchModel} that belongs to the User and by {@link BatchState}
     */
    @GetMapping("/batches/state")
    public ResponseEntity<?> getAllByBatchState(@RequestParam BatchState state) {
        return configure(batchAction.getAllByState(state));
    }

    /**
     * Update {@link Batch} -> {@link BatchState}
     *
     * @param state of {@link Batch}
     * @param id    of {@link Batch}
     * @return
     */
    @PutMapping("/batches/state")
    public ResponseEntity<?> updateBatchState(@RequestParam BatchState state,
                                              @RequestParam int id) {
        int userId = RequestContext.getUserId();
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
     * only description can be changed of a Batch.
     * <p>productId on Request Body will be ignored</p>
     * <i>User must have editor rights, i.e. {@link ObjectRight#OWNER}  or {@link ObjectRight#EDITOR} </i>
     *
     * @param dto
     * @return
     */
    @PutMapping("/batch")
    public ResponseEntity<?> updateBatch(@Validated @RequestBody BatchDto dto) {
        int editorId = RequestContext.getUserId();
        Batch batch = batchAction.getById(dto.getId());
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(dto.getId(), editorId), OBJECT_NOT_FOUND);
        batch.setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), batch.getDescription()));

        return configure(batchAction.save(batch));
    }

    /**
     * <p>check the user has {@link ObjectRight#OWNER} rights of the Batch</p>
     * <p>check the Batch does not have any States or {@link com.prodnees.domain.stage.StageTodo} associated with it</p>
     *
     * @param id
     * @return
     */
    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteBatch(@RequestParam int id) {
        int ownerId = RequestContext.getUserId();
        BatchRight batchRight = batchRightAction.getByBatchIdAndUserId(id, ownerId);
        LocalAssert.isTrue(batchRight.getObjectRight().equals(ObjectRight.OWNER), ACCESS_DENIED);
        batchAction.deleteById(id);
        return configure();
    }

    /**
     * <p>Save the {@link BatchRight}</p>
     * <p>Send email to the user for getting the rights</p>
     * <i>Only associates can be given {@link BatchRight}</i>
     *
     * @param dto
     * @return
     */
    @PutMapping("/batch-right")
    public ResponseEntity<?> saveBatchRight(@Validated @RequestBody BatchRightDto dto) {
        int adminId = RequestContext.getUserId();
        RequestContext.denySelfManagement(dto.getEmail());
        Assert.isTrue(dto.getObjectRightsType() != ObjectRight.OWNER, "you can only assign an editor or a  viewer");
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(adminId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        BatchRight batchRight = batchRightAction.getByBatchIdAndUserId(dto.getBatchId(), adminId);
        Assert.isTrue(batchRight.getObjectRight().equals(ObjectRight.OWNER),
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
     * @param batchId
     * @param servletRequest
     * @return
     */
    @GetMapping("/batch-right/{rightOf}")
    public ResponseEntity<?> getBatchProductRight(@PathVariable String rightOf,
                                                  @RequestParam Optional<Integer> batchId,
                                                  HttpServletRequest servletRequest) {
        int userId = RequestContext.getUserId();
        switch (rightOf) {
            case "batch-product":
                LocalAssert.isTrue(batchId.isPresent(), "batchId must be provided for batch-product rights");
                LocalAssert.isTrue(batchRightAction.hasBatchReaderRights(batchId.get(), userId), BATCH_NOT_FOUND);
                return configure(batchRightAction.getAllByBatchId(batchId.get()));
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
     * @param batchId
     * @param userId
     * @return
     */
    @DeleteMapping("/batch-right")
    public ResponseEntity<?> deleteBatchRight(@RequestParam int batchId, int userId) {
        int adminId = RequestContext.getUserId();
        Assert.isTrue(userId != adminId, "you cannot delete your own batch product rights");
        BatchRight adminBatchRight = batchRightAction.getByBatchIdAndUserId(batchId, adminId);// check you have permission, you are an owner
        Assert.isTrue(adminBatchRight.getObjectRight() == ObjectRight.OWNER,
                UPDATE_DENIED.getMessage());
        BatchRight userBatchRight = batchRightAction.getByBatchIdAndUserId(batchId, userId); // check the other user is not the owner
        Assert.isTrue(userBatchRight.getObjectRight() != ObjectRight.OWNER,
                "you cannot remove another owner's batch product rights");
        batchRightAction.deleteByBatchIdAndUserId(batchId, userId);
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

        int userId = RequestContext.getUserId();
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
        documentRightAction.addNew(documentRight);

        return configure();

    }


}
