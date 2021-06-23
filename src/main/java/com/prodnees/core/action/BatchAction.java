package com.prodnees.core.action;

import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;
import com.prodnees.core.dto.batch.BatchDto;
import com.prodnees.core.model.batch.BatchListModel;
import com.prodnees.core.model.batch.BatchModel;

import java.util.List;

public interface BatchAction {

    boolean existsById(int id);

    boolean existsByIdAndState(int id, BatchState state);

    boolean isEditable(int id);


    List<BatchModel> getAllByState(BatchState state);

    BatchModel save(Batch batch);

    BatchModel create(BatchDto dto);

    Batch getById(int id);

    BatchModel getModelById(int id);

    List<Batch> getAllByProductId(int productId);

    List<Batch> getAllByIds(Iterable<Integer> ids);

    BatchListModel getListModelByIds(Iterable<Integer> ids);

    void deleteById(int id);
}
