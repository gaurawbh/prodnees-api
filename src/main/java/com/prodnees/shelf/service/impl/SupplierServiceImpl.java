/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.service.impl;

import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.shelf.dao.ProductDao;
import com.prodnees.shelf.dao.SupplierDao;
import com.prodnees.shelf.domain.Product;
import com.prodnees.shelf.domain.Supplier;
import com.prodnees.shelf.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierDao supplierDao;
    private final ProductDao productDao;

    public SupplierServiceImpl(SupplierDao supplierDao, ProductDao productDao) {
        this.supplierDao = supplierDao;
        this.productDao = productDao;
    }

    @Override
    public boolean existsById(int id) {
        return supplierDao.existsById(id);
    }

    @Override
    public Supplier save(Supplier supplier) {
        if (supplierDao.existsByName(supplier.getName())) {
            throw new NeesBadRequestException(String.format("Supplier with name: %s already exists. Supplier name must be unique.", supplier.getName()));
        }
        return supplierDao.save(supplier);
    }

    @Override
    public Supplier getById(int id) {
        return supplierDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("supplier with id: %d not found", id)));
    }

    @Override
    public List<Supplier> findAll() {
        return supplierDao.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return supplierDao.existsByName(name);
    }

    @Override
    public Supplier update(Supplier dto) {
        getById(dto.getId());
        return supplierDao.save(dto);
    }

    public List<Supplier> findAllByProductNameLike(String productNameLike) {
        String wildCard = String.format("%%%s%%", productNameLike);
        List<Product> productList = productDao.getAllByNameLike(wildCard);
        List<Supplier> supplierList = new ArrayList<>();
        Map<Integer, Supplier> supplierMap = new HashMap<>();
        productList.forEach(product ->
                supplierMap.put(product.getSupplierId(), getById(product.getSupplierId())));
        Set<Integer> supplerIdList = supplierMap.keySet();
        supplerIdList.forEach(integer ->
                supplierList.add(supplierMap.get(integer)));
        return supplierList;
    }

    @Override
    public void deleteById(int id) {
        supplierDao.deleteById(id);
    }
}
