package com.prodnees.service;

import com.prodnees.domain.Product;

public interface ProductService {

    Product save(Product product);

    Product getById(int id);

    Product getByName(String name);
}
