package com.prodnees.shelf.service;

import com.prodnees.core.dto.ProductDto;
import com.prodnees.shelf.domain.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product addNew(ProductDto dto);

    Product update(ProductDto dto);

    Product getById(int id);

    Product getByName(String name);

    List<Product> getAllByIds(Iterable<Integer> productIdIterable);

    List<Product> findAll();

    void deleteById(int id);

    boolean existsById(int id);
}
