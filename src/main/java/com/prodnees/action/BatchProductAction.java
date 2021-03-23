package com.prodnees.action;

import com.prodnees.domain.BatchProduct;
import com.prodnees.model.BatchProductModel;

import java.util.List;

public interface BatchProductAction {

    boolean existsById(int id);

    BatchProductModel save(BatchProduct batchProduct);

    BatchProduct getById(int id);

    BatchProductModel getModelById(int id);

    List<BatchProduct> getAllByProductId(int productId);

    List<BatchProductModel> getAllByIds(Iterable<Integer> batchProductIds);

    void deleteById(int id);
}
