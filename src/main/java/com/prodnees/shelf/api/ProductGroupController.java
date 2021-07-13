package com.prodnees.shelf.api;

import com.prodnees.core.web.response.LocalResponse;
import com.prodnees.shelf.domain.ProductGroup;
import com.prodnees.shelf.service.ProductGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductGroupController {

    private final ProductGroupService productGroupService;

    public ProductGroupController(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @PostMapping("/product-group")
    public ResponseEntity<?> addProductGroup(@Validated @RequestBody ProductGroup productGroup){
        return LocalResponse.configure(productGroupService.addProductGroup(productGroup));


    }

    @GetMapping("/product-groups")
    public ResponseEntity<?> findAllProductGroups() {
        return LocalResponse.configure(productGroupService.findAll());
    }
}
