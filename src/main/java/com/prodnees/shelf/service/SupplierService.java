/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.service;

import com.prodnees.shelf.domain.Supplier;

import java.util.List;

public interface SupplierService {

    boolean existsById(int id);

    Supplier save(Supplier supplier);

    Supplier getById(int id);

    List<Supplier> findAll();

    boolean existsByName(String name);

    Supplier update(Supplier supplier);

    List<Supplier> findAllByProductNameLike(String productNameLike);

    void deleteById(int id);
}
