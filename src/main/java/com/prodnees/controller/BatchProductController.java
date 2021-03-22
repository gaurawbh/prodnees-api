package com.prodnees.controller;

import com.prodnees.action.BatchProductAction;
import com.prodnees.action.EventAction;
import com.prodnees.action.StateAction;
import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.BatchProductStatus;
import com.prodnees.domain.rels.BatchProductRights;
import com.prodnees.domain.rels.ObjectRightsType;
import com.prodnees.dto.BatchProductDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.model.BatchProductModel;
import com.prodnees.service.rels.BatchProductRightsService;
import com.prodnees.util.MapperUtil;
import com.prodnees.util.ValidatorUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class BatchProductController {
    private final UserValidator userValidator;
    private final BatchProductAction batchProductAction;
    private final BatchProductRightsService batchProductRightsService;
    private final StateAction stateAction;
    private final EventAction eventAction;

    public BatchProductController(UserValidator userValidator,
                                  BatchProductAction batchProductAction,
                                  BatchProductRightsService batchProductRightsService,
                                  StateAction stateAction,
                                  EventAction eventAction) {
        this.userValidator = userValidator;
        this.batchProductAction = batchProductAction;
        this.batchProductRightsService = batchProductRightsService;
        this.stateAction = stateAction;
        this.eventAction = eventAction;
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
    public ResponseEntity<?> save(@Validated @RequestBody BatchProductDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        dto.setId(0);
        BatchProduct batchProduct = MapperUtil.getDozer().map(dto, BatchProduct.class);
        batchProduct.setCreatedDate(LocalDate.now()).setStatus(BatchProductStatus.INITIAL);
        BatchProductModel batchProductModel = batchProductAction.save(batchProduct);
        batchProductRightsService.save(new BatchProductRights()
                .setUserId(userId)
                .setBatchProductId(batchProductModel.getId())
                .setObjectRightsType(ObjectRightsType.OWNER));
        return configure(batchProductModel);
    }

    @GetMapping("/batch-products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int ownerId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Optional<BatchProductRights> batchProductRights = batchProductRightsService.findByBatchProductIdAndOwnerId(integer, ownerId);
            Assert.isTrue(batchProductRights.isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(batchProductAction.getModelById(batchProductRights.get().getBatchProductId()));
        }, () -> {
            Iterable<Integer> batchProductIds = batchProductRightsService.getAllByOwnerId(ownerId)
                    .stream()
                    .map(BatchProductRights::getBatchProductId)
                    .collect(Collectors.toList());
            atomicReference.set(batchProductAction.getAllByIds(batchProductIds));
        });
        return configure(atomicReference.get());
    }

    /**
     * only name and description can be changed of a BatchProduct.
     * <p>productId on Request Body will be ignored</p>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PutMapping("/batch-product")
    public ResponseEntity<?> update(@Validated @RequestBody BatchProductDto dto,
                                    HttpServletRequest servletRequest) {
        int editorId = userValidator.extractUserId(servletRequest);
        Assert.isTrue(batchProductAction.existsById(dto.getId()), OBJECT_NOT_FOUND.getMessage());
        Assert.isTrue(batchProductRightsService.hasBatchProductEditorRights(dto.getId(), editorId), OBJECT_NOT_FOUND.getMessage());
        BatchProduct batchProduct = batchProductAction.getById(dto.getId());
        batchProduct.setName(dto.getName())
                .setDescription(ValidatorUtil.ifValidOrElse(dto.getDescription(), batchProduct.getDescription()));

        return configure(batchProductAction.save(batchProduct));
    }

    /**
     * <p>check the user is an Owner of the Batch Product</p>
     * <p>check the Batch Product does not have any States or Events associated with it</p>
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @DeleteMapping("/batch-product")
    public ResponseEntity<?> delete(@RequestParam int id,
                                    HttpServletRequest servletRequest) {
        int ownerId = userValidator.extractUserId(servletRequest);
        Optional<BatchProductRights> batchProductRightsOptional = batchProductRightsService.findByBatchProductIdAndOwnerId(id, ownerId);
        batchProductRightsOptional.ifPresentOrElse(batchProductRights -> {
            Assert.isTrue(batchProductRights.getObjectRightsType().equals(ObjectRightsType.OWNER), ACCESS_DENIED.getMessage());
            Assert.isTrue(!stateAction.existsByBatchProductId(id), REFERENCED_OBJECT.getMessage());
            Assert.isTrue(eventAction.existsByBatchProductId(id), REFERENCED_OBJECT.getMessage());
            batchProductAction.deleteById(id);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure();
    }



}
