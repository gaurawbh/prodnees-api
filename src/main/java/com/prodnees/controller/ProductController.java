package com.prodnees.controller;

import com.prodnees.domain.Product;
import com.prodnees.domain.rels.ObjectRightsType;
import com.prodnees.domain.rels.ProductRights;
import com.prodnees.dto.ProductDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.service.ProductService;
import com.prodnees.service.rels.ProductRightsService;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import static com.prodnees.config.constants.APIErrors.ACCESS_DENIED;
import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
import static com.prodnees.web.response.SuccessResponse.configure;


@RestController
@RequestMapping("/secure")
@CrossOrigin
@Transactional
public class ProductController {
    private final ProductService productService;
    private final UserValidator userValidator;
    private final ProductRightsService productRightsService;

    public ProductController(ProductService productService,
                             UserValidator userValidator,
                             ProductRightsService productRightsService) {
        this.productService = productService;
        this.userValidator = userValidator;
        this.productRightsService = productRightsService;
    }

    @PostMapping("/product")
    public ResponseEntity<?> save(@Validated @RequestBody ProductDto dto,
                                  HttpServletRequest servletRequest) {
        dto.setId(0);
        int ownerId = userValidator.extractUserId(servletRequest);
        Product product = MapperUtil.getDozer().map(dto, Product.class);
        product = productService.save(product);
        productRightsService.save(new ProductRights()
                .setUserId(ownerId)
                .setProductId(product.getId())
                .setObjectRightsType(ObjectRightsType.OWNER));
        return configure(product);
    }

    @GetMapping("/products")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Assert.isTrue(productRightsService.findByProductIdAndUserId(integer, userId).isPresent(), OBJECT_NOT_FOUND.getMessage());
            atomicReference.set(productService.getById(integer));
        }, () -> {
            Iterable<Integer> productIdIterable = productRightsService.getAllByUserId(userId).stream().map(ProductRights::getProductId).collect(Collectors.toList());
            atomicReference.set(productService.getAllByIds(productIdIterable));
        });

        return SuccessResponse.configure(atomicReference.get());
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> update(@RequestParam int id,
                                    HttpServletRequest servletRequest) {

        int userId = userValidator.extractUserId(servletRequest);
        Optional<ProductRights> batchProductRightsOpt = productRightsService.findByProductIdAndUserId(id, userId);
        batchProductRightsOpt.ifPresentOrElse(productRights -> {
            Assert.isTrue(productRights.getObjectRightsType().equals(ObjectRightsType.OWNER), ACCESS_DENIED.getMessage());
            productService.deleteById(productRights.getProductId());
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure();
    }

}
