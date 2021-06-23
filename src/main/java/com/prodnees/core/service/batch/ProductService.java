package com.prodnees.core.service.batch;

import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.dto.ProductDto;

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
