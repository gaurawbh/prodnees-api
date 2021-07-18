package com.prodnees.shelf.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.auth.action.UserAction;
import com.prodnees.core.dto.ProductDto;
import com.prodnees.core.web.response.LocalResponse;
import com.prodnees.shelf.action.ProductMetadataService;
import com.prodnees.shelf.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static com.prodnees.core.web.response.LocalResponse.configure;


@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductMetadataService productMetadataService;
    private final UserAction userAction;

    public ProductController(ProductService productService,
                             ProductMetadataService productMetadataService, UserAction userAction) {
        this.productService = productService;
        this.productMetadataService = productMetadataService;
        this.userAction = userAction;
    }

    @GetMapping("/product/fields")
    public ResponseEntity<?> getProductFields() {
        return LocalResponse.configure(productMetadataService.getAllProductFields(false));

    }

    @PostMapping("/product")
    public ResponseEntity<?> addNewProduct(@RequestBody Map<String, Object> requestBody) throws JsonProcessingException {
//        return configure(productService.addProduct(dto));
        return configure(productService.addProductEx(requestBody));
    }

    @GetMapping(value = {"/products", "/products/{id}"})
    public ResponseEntity<?> getProductById(@PathVariable Optional<Integer> id) {
        if (id.isPresent()) {
            return configure(productService.getById(id.get()));
        } else {
            return configure(productService.findAll());
        }
    }

    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@Validated @RequestBody ProductDto dto) throws JsonProcessingException {
        return configure(productService.update(dto));
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@RequestParam int id) {
        productService.deleteById(id);
        return configure("successfully deleted product with id: " + id);
    }

}
