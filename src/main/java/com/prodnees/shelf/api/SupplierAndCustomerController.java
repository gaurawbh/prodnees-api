/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.api;

import com.prodnees.core.util.LocalAssert;
import com.prodnees.shelf.domain.Customer;
import com.prodnees.shelf.domain.Supplier;
import com.prodnees.shelf.service.CustomerService;
import com.prodnees.shelf.service.SupplierService;
import org.springframework.http.ResponseEntity;
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

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.prodnees.core.web.response.LocalResponse.configure;


@RestController
@RequestMapping("/secure/admin/acnt/")
@CrossOrigin
public class SupplierAndCustomerController {
    private final SupplierService supplierService;
    private final CustomerService customerService;


    public SupplierAndCustomerController(SupplierService supplierService,
                                         CustomerService customerService) {
        this.supplierService = supplierService;
        this.customerService = customerService;
    }

    @PostMapping("/supplier")
    public ResponseEntity<?> save(@Validated @RequestBody Supplier supplier) {
        return configure(supplierService.save(supplier));
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<?> getSupplierById(@PathVariable Optional<Integer> id) {
        if (id.isPresent()) {
            return configure(supplierService.getById(id.get()));
        } else {
            return configure(supplierService.findAll());
        }
    }

    @PutMapping("/supplier")
    public ResponseEntity<?> update(@Validated @RequestBody Supplier supplier) {
        return configure(supplierService.update(supplier));
    }

    @GetMapping("/suppliers/product")
    public ResponseEntity<?> getAllByProductName(@RequestParam String productNameLike) {
        return configure(supplierService.findAllByProductNameLike(productNameLike));

    }

    @DeleteMapping("/supplier")
    public ResponseEntity<?> deleteSupplier(@RequestParam int id) {
        LocalAssert.isTrue(supplierService.existsById(id), String.format("Supplier with id: %d does not exist", id));
        supplierService.deleteById(id);
        return configure(String.format("Supplier with id: %d successfully deleted", id));

    }

    @PostMapping("/customer")
    public ResponseEntity<?> save(@Validated @RequestBody Customer customer) {
        return configure(customerService.save(customer));
    }

    @GetMapping("/customers")
    public ResponseEntity<?> getCustomer(@RequestParam Optional<Integer> id) {
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            atomicReference.set(customerService.getById(integer));
        }, () -> atomicReference.set(customerService.findAll()));

        return configure(atomicReference.get());
    }

    @PutMapping("/customer")
    public ResponseEntity<?> update(@Validated @RequestBody Customer customer) {
        return configure(customerService.update(customer));
    }

    @DeleteMapping("/customer")
    public ResponseEntity<?> delete(@RequestParam int id) {
        LocalAssert.isTrue(customerService.existsById(id), String.format("Customer with id: %d does not exist", id));
        customerService.deleteById(id);
        return configure(String.format("Customer with id: %d successfully deleted", id));
    }

}
