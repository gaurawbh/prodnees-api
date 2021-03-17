package com.prodnees.service;

import com.prodnees.domain.BatchProduct;

import java.util.List;
public interface BatchProductService {

    BatchProduct save(BatchProduct batchProduct);

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);
}
