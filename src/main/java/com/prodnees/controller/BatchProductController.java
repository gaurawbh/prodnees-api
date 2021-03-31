package com.prodnees.controller;

import com.prodnees.action.BatchProductAction;
import com.prodnees.action.EventAction;
import com.prodnees.action.StateAction;
import com.prodnees.action.rel.BatchProductRightAction;
import com.prodnees.action.rel.DocumentRightAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.ApprovalDocumentState;
import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.BatchProductApprovalDocument;
import com.prodnees.domain.BatchProductStatus;
import com.prodnees.domain.rels.Associates;
import com.prodnees.domain.rels.BatchProductRight;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.domain.rels.ObjectRightType;
import com.prodnees.dto.BatchProductApprovalDocumentDto;
import com.prodnees.dto.BatchProductDto;
import com.prodnees.dto.BatchProductRightDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.BatchProductModel;
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
import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.config.constants.APIErrors.REFERENCED_OBJECT;
import static com.prodnees.config.constants.APIErrors.UPDATE_DENIED;
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class BatchProductController {
    private final RequestValidator requestValidator;
    private final BatchProductAction batchProductAction;
    private final BatchProductRightAction batchProductRightAction;
    private final StateAction stateAction;
    private final EventAction eventAction;
    private final AssociatesService associatesService;
    private final DocumentRightAction documentRightAction;
    private final BatchProductApprovalDocumentService batchProductApprovalDocumentService;

    public BatchProductController(RequestValidator requestValidator,
                                  BatchProductAction batchProductAction,
                                  BatchProductRightAction batchProductRightAction,
                                  StateAction stateAction,
                                  EventAction eventAction,
                                  AssociatesService associatesService,
                                  DocumentRightAction documentRightAction,
                                  BatchProductApprovalDocumentService batchProductApprovalDocumentService) {
        this.requestValidator = requestValidator;
        this.batchProductAction = batchProductAction;
        this.batchProductRightAction = batchProductRightAction;
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

    @PostMapping("/batch-product")
    public ResponseEntity<?> saveBatchProduct(@Validated @RequestBody BatchProductDto dto,
                                              HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        dto.setId(0);
        BatchProduct batchProduct = MapperUtil.getDozer().map(dto, BatchProduct.class);
        batchProduct.setCreatedDate(LocalDate.now()).setStatus(BatchProductStatus.INITIAL);
        BatchProductModel batchProductModel = batchProductAction.save(batchProduct);
        batchProductRightAction.save(new BatchProductRight()
                .setUserId(userId)
                .setBatchProductId(batchProductModel.getId())
                .setObjectRightsType(ObjectRightType.OWNER));
        return configure(batchProductModel);
    }

    @GetMapping("/batch-products")
    public ResponseEntity<?> getBatchProducts(@RequestParam Optional<Integer> id,
                                              HttpServletRequest servletRequest) {
        int ownerId = requestValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Optional<BatchProductRight> batchProductRights = batchProductRightAction.findByBatchProductIdAndUserId(integer, ownerId);
            Assert.isTrue(batchProductRights.isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(batchProductAction.getModelById(batchProductRights.get().getBatchProductId()));
        }, () -> {
            Iterable<Integer> batchProductIds = batchProductRightAction.getAllByOwnerId(ownerId)
                    .stream()
                    .map(BatchProductRight::getBatchProductId)
                    .collect(Collectors.toList());
            atomicReference.set(batchProductAction.getAllByIds(batchProductIds));
        });
        return configure(atomicReference.get());
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
    @PutMapping("/batch-product")
    public ResponseEntity<?> updateBatchProduct(@Validated @RequestBody BatchProductDto dto,
                                                HttpServletRequest servletRequest) {
        int editorId = requestValidator.extractUserId(servletRequest);
        Assert.isTrue(batchProductAction.existsById(dto.getId()), OBJECT_NOT_FOUND.getMessage());
        Assert.isTrue(batchProductRightAction.hasBatchProductEditorRights(dto.getId(), editorId), OBJECT_NOT_FOUND.getMessage());
        BatchProduct batchProduct = batchProductAction.getById(dto.getId());
        batchProduct.setName(dto.getName())
                .setDescription(ValidatorUtil.ifValidOrElse(dto.getDescription(), batchProduct.getDescription()));

        return configure(batchProductAction.save(batchProduct));
    }

    /**
     * <p>check the user has {@link ObjectRightType#OWNER} rights of the Batch Product</p>
     * <p>check the Batch Product does not have any States or Events associated with it</p>
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @DeleteMapping("/batch-product")
    public ResponseEntity<?> deleteBatchProduct(@RequestParam int id,
                                                HttpServletRequest servletRequest) {
        int ownerId = requestValidator.extractUserId(servletRequest);
        Optional<BatchProductRight> batchProductRightsOptional = batchProductRightAction.findByBatchProductIdAndUserId(id, ownerId);
        batchProductRightsOptional.ifPresentOrElse(batchProductRights -> {
            Assert.isTrue(batchProductRights.getObjectRightsType().equals(ObjectRightType.OWNER), ACCESS_DENIED.getMessage());
            Assert.isTrue(!stateAction.existsByBatchProductId(id), REFERENCED_OBJECT.getMessage());
            Assert.isTrue(eventAction.existsByBatchProductId(id), REFERENCED_OBJECT.getMessage());
            batchProductAction.deleteById(id);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure();
    }

    /**
     * <p>Save the {@link BatchProductRight}</p>
     * <p>Send email to the user for getting the rights</p>
     * <i>Only associates can be given {@link BatchProductRight}</i>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PutMapping("/batch-product-right")
    public ResponseEntity<?> saveBatchProductRight(@Validated @RequestBody BatchProductRightDto dto,
                                                   HttpServletRequest servletRequest) {
        int adminId = requestValidator.extractUserId(servletRequest);
        Assert.isTrue(dto.getObjectRightsType() != ObjectRightType.OWNER, "you can only assign an editor or a  viewer");
        Assert.isTrue(associatesService.existsByAdminIdAndAssociateEmail(adminId, dto.getEmail()), APIErrors.ASSOCIATES_ONLY.getMessage());
        Optional<BatchProductRight> batchProductRightsOpt = batchProductRightAction.findByBatchProductIdAndUserId(dto.getBatchProductId(), adminId);
        Assert.isTrue(batchProductRightsOpt.isPresent() && batchProductRightsOpt.get().getObjectRightsType().equals(ObjectRightType.OWNER),
                "only owners can invite others to admin their batch product or update the rights");
        return configure(batchProductRightAction.save(dto));
    }

    /**
     * if rightOf path is product,
     * <i>return {@link java.util.List} of {@link BatchProductRight} by batchProductId</i>
     * <p>if rightOf path is user,</p>
     * <i>return {@link java.util.List} of {@link BatchProductRight} by userId</i>
     *
     * @param rightOf
     * @param batchProductId
     * @param servletRequest
     * @return
     */
    @GetMapping("/batch-product-right/{rightOf}")
    public ResponseEntity<?> getBatchProductRight(@PathVariable String rightOf,
                                                  @RequestParam Optional<Integer> batchProductId,
                                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();

        switch (rightOf) {
            case "product":
                return null;
            case "user":
                return null;
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
    @DeleteMapping("/batch-product-right")
    public ResponseEntity<?> deleteBatchProductRights(@RequestParam int batchProductId, int userId,
                                                      HttpServletRequest servletRequest) {
        int adminId = requestValidator.extractUserId(servletRequest);
        Assert.isTrue(userId != adminId, "you cannot delete your own batch product rights");
        Optional<BatchProductRight> adminBatchProductRightOpt = batchProductRightAction.findByBatchProductIdAndUserId(batchProductId, adminId);// check you have permission, you are an owner
        Assert.isTrue(adminBatchProductRightOpt.isPresent() && adminBatchProductRightOpt.get().getObjectRightsType() == ObjectRightType.OWNER,
                UPDATE_DENIED.getMessage());
        Optional<BatchProductRight> userBatchProductRightOpt = batchProductRightAction.findByBatchProductIdAndUserId(batchProductId, userId); // check the other user is not the owner
        Assert.isTrue(userBatchProductRightOpt.isPresent() && userBatchProductRightOpt.get().getObjectRightsType() != ObjectRightType.OWNER,
                "you cannot remove another owner's batch product rights");
        batchProductRightAction.deleteByBatchProductIdAndUserId(batchProductId, userId);
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
    @PostMapping("/batch-product/approval-document")
    public ResponseEntity<?> addApprovalDocument(@RequestBody @Validated BatchProductApprovalDocumentDto dto,
                                                 HttpServletRequest servletRequest) {

        int userId = requestValidator.extractUserId(servletRequest);
        Optional<Associates> associatesOptional = associatesService.findByAdminIdAndAssociateEmail(userId, dto.getApproverEmail());
        LocalAssert.isTrue(associatesOptional.isPresent(), "approver must be an associate.");
        LocalAssert.isTrue(documentRightAction.existsByDocumentIdAndUserId(dto.getDocumentId(), userId), "document not found");
        LocalAssert.isTrue(batchProductRightAction.hasBatchProductEditorRights(dto.getBatchProductId(), userId), OBJECT_NOT_FOUND);
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

        return LocalResponse.configure();

    }


}
