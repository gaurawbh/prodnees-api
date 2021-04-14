package com.prodnees.action;

import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.model.batch.BatchListModel;
import com.prodnees.model.batch.BatchModel;
import java.util.List;

public interface BatchAction {

    boolean existsById(int id);

    boolean existsByIdAndStatus(int id, BatchStatus status);

    boolean isEditable(int id);

    List<BatchModel> getAllByUserIdAndStatus(int userId, BatchStatus status);

    BatchModel save(Batch batch);

    Batch getById(int id);

    BatchModel getModelById(int id);

    List<Batch> getAllByProductId(int productId);

    List<Batch> getAllByIds(Iterable<Integer> ids);

    BatchListModel getListModelByIds(Iterable<Integer> ids);

    void deleteById(int id);
}
