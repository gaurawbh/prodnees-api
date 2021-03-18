package com.prodnees.controller;

import com.prodnees.action.BatchProductAction;
import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.rels.BatchProductRights;
import com.prodnees.domain.rels.ObjectRightsType;
import com.prodnees.dto.BatchProductDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.model.BatchProductModel;
import com.prodnees.service.rels.BatchProductRightsService;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.web.response.SuccessResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class BatchProductController {
    private final UserValidator userValidator;
    private final BatchProductAction batchProductAction;
    private final BatchProductRightsService batchProductRightsService;

    public BatchProductController(UserValidator userValidator, BatchProductAction batchProductAction,
                                  BatchProductRightsService batchProductRightsService) {
        this.userValidator = userValidator;
        this.batchProductAction = batchProductAction;
        this.batchProductRightsService = batchProductRightsService;
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
        BatchProductModel batchProductModel = batchProductAction.save(batchProduct);
        batchProductRightsService.save(new BatchProductRights().setUserId(userId).setBatchProductId(batchProductModel.getId()).setObjectRightsType(ObjectRightsType.OWNER));
        return configure(batchProductModel);
    }

    @GetMapping("/batch-products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int ownerId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Optional<BatchProductRights> batchProductRights = batchProductRightsService.getByBatchProductIdAndOwnerId(integer, ownerId);
            Assert.isTrue(batchProductRights.isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(batchProductAction.getById(batchProductRights.get().getBatchProductId()));
        }, () -> {
            Iterable<Integer> batchProductIds = batchProductRightsService.getAllByOwnerId(ownerId)
                    .stream()
                    .map(BatchProductRights::getBatchProductId)
                    .collect(Collectors.toList());
            atomicReference.set(batchProductAction.getAllByIds(batchProductIds));
        });
        return configure(atomicReference.get());
    }

    @PutMapping("/batch-product")
    public ResponseEntity<?> update(@Validated @RequestBody BatchProductDto dto,
                                    HttpServletRequest servletRequest) {
        return configure();
    }


}
