package com.prodnees.shelf.api;

import com.prodnees.auth.action.UserAction;
import com.prodnees.core.dto.ProductDto;
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

import static com.prodnees.core.web.response.LocalResponse.configure;


@RestController
public class ProductController {
    private final ProductService productService;
    private final UserAction userAction;

    public ProductController(ProductService productService,
                             UserAction userAction) {
        this.productService = productService;
        this.userAction = userAction;
    }

    @PostMapping("/product")
    public ResponseEntity<?> addNewProduct(@Validated @RequestBody ProductDto dto) {
        return configure(productService.addNew(dto));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) {
        return configure(productService.getById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<?> findAllProducts() {
        return configure(productService.findAll());
    }

    @PutMapping("/product")
    public ResponseEntity<?> updateProduct(@Validated @RequestBody ProductDto dto) {
        return configure(productService.update(dto));
    }

    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@RequestParam int id) {
        productService.deleteById(id);
        return configure("successfully deleted product with id: " + id);
    }

}
