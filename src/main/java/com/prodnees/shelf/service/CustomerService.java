/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.service;

import com.prodnees.shelf.domain.Customer;

import java.util.List;

public interface CustomerService {

    boolean existsById(int id);

    Customer save(Customer customer);

    Customer getById(int id);

    List<Customer> findAll();

    boolean existsByName(String name);

    Customer update(Customer customer);

    void deleteById(int id);
}
