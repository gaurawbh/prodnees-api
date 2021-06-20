package com.prodnees.service.batch;

import com.prodnees.domain.batch.Product;
import com.prodnees.dto.ProductDto;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product addNew(ProductDto dto);

    Product update(ProductDto dto);

    Product getById(int id);

    Product getByName(String name);

    List<Product> getAllByIds(Iterable<Integer> productIdIterable);

    void deleteById(int id);

    boolean existsById(int id);
}
