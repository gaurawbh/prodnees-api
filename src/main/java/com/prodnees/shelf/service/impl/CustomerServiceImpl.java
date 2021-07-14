/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.service.impl;

import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.shelf.dao.CustomerDao;
import com.prodnees.shelf.domain.Customer;
import com.prodnees.shelf.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;

    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public boolean existsById(int id) {
        return customerDao.existsById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer getById(int id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("customer with id: %d not found", id)));
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return customerDao.existsByName(name);
    }

    @Override
    public Customer update(Customer dto) {
        getById(dto.getId());
        return customerDao.save(dto);
    }

    @Override
    public void deleteById(int id) {
        customerDao.deleteById(id);
    }
}
