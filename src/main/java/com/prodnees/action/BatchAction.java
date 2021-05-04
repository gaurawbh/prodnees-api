package com.prodnees.action;

import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.model.batch.BatchListModel;
import com.prodnees.model.batch.BatchModel;

import java.util.List;

public interface BatchAction {

    boolean existsById(int id);

    boolean existsByIdAndState(int id, BatchState state);

    boolean isEditable(int id);

    List<BatchModel> getAllByUserIdAndState(int userId, BatchState state);

    BatchModel save(Batch batch);

    Batch getById(int id);

    BatchModel getModelById(int id);

    List<Batch> getAllByProductId(int productId);

    List<Batch> getAllByIds(Iterable<Integer> ids);

    BatchListModel getListModelByIds(Iterable<Integer> ids);

    void deleteById(int id);
}
