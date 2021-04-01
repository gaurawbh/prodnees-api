package com.prodnees.service;

import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.BatchProductStatus;
import java.util.List;
public interface BatchProductService {

    BatchProduct save(BatchProduct batchProduct);

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);

    List<BatchProduct> getAllByIds(Iterable<Integer> batchProductIds);

    boolean existsById(int id);

    void deleteById(int id);

    boolean existsByIdAndStatus(int id, BatchProductStatus status);

    List<BatchProduct> getAllByUserIdAndStatus(int userId, BatchProductStatus status);
}
