package com.prodnees.shelf.service;

import com.prodnees.shelf.domain.ProductGroup;

import java.util.List;

public interface ProductGroupService {

    ProductGroup getById(int id);

    List<ProductGroup> findAll();

    ProductGroup addProductGroup(ProductGroup productGroup);

    void deleteById(int id);

    ProductGroup updateProductGroup(ProductGroup productGroup);
}
