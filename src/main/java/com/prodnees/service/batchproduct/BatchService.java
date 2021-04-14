package com.prodnees.service.batchproduct;

import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import java.util.List;

public interface BatchService {

    Batch save(Batch batch);

    Batch getById(int id);

    List<Batch> getAllByProductId(int productId);

    List<Batch> getAllByIds(Iterable<Integer> ids);

    boolean existsById(int id);

    void deleteById(int id);

    boolean existsByIdAndStatus(int id, BatchStatus status);

    List<Batch> getAllByUserIdAndStatus(int userId, BatchStatus status);
}
