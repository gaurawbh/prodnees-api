package com.prodnees.service;

import com.prodnees.domain.BatchProduct;
import com.prodnees.model.BatchProductModel;

import java.util.List;
public interface BatchProductService {

    BatchProduct save(BatchProduct batchProduct);

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);

    List<BatchProduct> getAllByIds(Iterable<Integer> batchProductIds);
}
