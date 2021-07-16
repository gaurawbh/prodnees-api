package com.prodnees.shelf.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.core.dto.ProductDto;
import com.prodnees.shelf.domain.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product addProduct(ProductDto dto) throws JsonProcessingException;

    Product update(ProductDto dto) throws JsonProcessingException;

    Product getById(int id);

    Product getByName(String name);

    List<Product> getAllByIds(Iterable<Integer> productIdIterable);

    List<Product> findAll();

    void deleteById(int id);

    boolean existsById(int id);
}
