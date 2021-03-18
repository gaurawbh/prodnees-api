package com.prodnees.service;

import com.prodnees.domain.Product;
import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product getById(int id);

    Product getByName(String name);

    List<Product> getAllByIds(Iterable<Integer> productIdIterable);

    void deleteById(int id);
}
