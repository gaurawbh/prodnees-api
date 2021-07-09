package com.prodnees.core.action.stage;

import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.core.dto.stage.StageTodoDto;

import java.util.List;

public interface StageTodoAction {

    boolean existsByBatchId(int batchId);

    StageTodo addNew(StageTodoDto dto, int batchId);

    StageTodo getById(int id);

    List<StageTodo> getAllByBatchId(int batchId);

    List<StageTodo> getAllByStageId(int stageId);


    void deleteById(int id);
}
