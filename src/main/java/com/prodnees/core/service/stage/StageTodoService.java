package com.prodnees.core.service.stage;

import com.prodnees.core.domain.stage.StageTodo;

import java.util.List;
import java.util.Optional;

public interface StageTodoService {

    boolean existsByBatchId(int batchId);

    StageTodo save(StageTodo stageTodo);

    StageTodo getById(int id);

    List<StageTodo> getAllByBatchId(int batchId);

    List<StageTodo> getAllByStageId(int stageId);

    StageTodo getByBatchIdAndName(int batchId, String name);

    Optional<StageTodo> findById(int id);

    void deleteById(int id);
}
