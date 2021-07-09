package com.prodnees.core.service.batch;

import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;

import java.util.List;
import java.util.Optional;

public interface BatchService {

    Batch save(Batch batch);

    Batch getById(int id);

    Optional<Batch> findById(int id);

    List<Batch> getAllByProductId(int productId);

    List<Batch> getAllByIds(Iterable<Integer> ids);

    boolean existsById(int id);

    void deleteById(int id);

    boolean existsByIdAndState(int id, BatchState state);

    List<Batch> getAllByUserIdAndState(int userId, BatchState state);

    int getNextId();

    List<Batch> findAll();
}
