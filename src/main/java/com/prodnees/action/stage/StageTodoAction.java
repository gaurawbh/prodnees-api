package com.prodnees.action.stage;

import com.prodnees.domain.stage.StageTodo;
import java.util.List;

public interface StageTodoAction {

    boolean existsByBatchId(int batchId);

    StageTodo save(StageTodo stageTodo);

    StageTodo getById(int id);

    List<StageTodo> getAllByBatchId(int batchId);

    List<StageTodo> getAllByStageId(int stageId);

}
