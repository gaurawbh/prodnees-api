package com.prodnees.dao.stage;

import com.prodnees.domain.stage.StageTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StageTodoDao extends JpaRepository<StageTodo, Integer> {

    StageTodo getById(int id);

    List<StageTodo> getAllByBatchId(int batchId);

    List<StageTodo> getAllByStageId(int stageId);

    StageTodo getByBatchIdAndName(int batchId, String name);
    boolean existsByStageIdAndName(int stageId, String name);

    boolean existsByBatchId(int batchId);
}
