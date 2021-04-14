package com.prodnees.action;

import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.model.BatchProductModel;
import java.util.List;

public interface BatchAction {

    boolean existsById(int id);

    boolean existsByIdAndStatus(int id, BatchStatus status);

    boolean isEditable(int id);

    List<BatchProductModel> getAllByUserIdAndStatus(int userId, BatchStatus status);

    BatchProductModel save(Batch batch);

    Batch getById(int id);

    BatchProductModel getModelById(int id);

    List<Batch> getAllByProductId(int productId);

    List<BatchProductModel> getAllByIds(Iterable<Integer> batchProductIds);

    void deleteById(int id);
}
