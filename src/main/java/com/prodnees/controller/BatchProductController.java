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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
        return SuccessResponse.configure(batchProductModel);
    }

    @GetMapping("/")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return SuccessResponse.configure();
    }


}
