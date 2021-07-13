package com.prodnees.shelf.service;

import com.prodnees.shelf.domain.ProductGroup;

import java.util.List;

public interface ProductGroupService {

    List<ProductGroup> findAll();

    ProductGroup addProductGroup(ProductGroup productGroup);
}
