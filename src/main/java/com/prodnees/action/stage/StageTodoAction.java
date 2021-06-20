package com.prodnees.action.stage;

import com.prodnees.domain.stage.StageTodo;
import com.prodnees.dto.stage.StageTodoDto;

import java.util.List;
import java.util.Optional;

public interface StageTodoAction {

    boolean existsByBatchId(int batchId);

    StageTodo save(StageTodo stageTodo);

    StageTodo addNew(StageTodoDto dto, int batchId);

    StageTodo getById(int id);

    List<StageTodo> getAllByBatchId(int batchId);

    List<StageTodo> getAllByStageId(int stageId);

    Optional<StageTodo> findById(int id);

    void deleteById(int id);
}
