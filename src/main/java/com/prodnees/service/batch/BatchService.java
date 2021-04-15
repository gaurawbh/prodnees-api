package com.prodnees.service.batch;

import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchState;
import java.util.List;

public interface BatchService {

    Batch save(Batch batch);

    Batch getById(int id);

    List<Batch> getAllByProductId(int productId);

    List<Batch> getAllByIds(Iterable<Integer> ids);

    boolean existsById(int id);

    void deleteById(int id);

    boolean existsByIdAndState(int id, BatchState state);

    List<Batch> getAllByUserIdAndState(int userId, BatchState state);
}
