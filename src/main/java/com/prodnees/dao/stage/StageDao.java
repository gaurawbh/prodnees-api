package com.prodnees.dao.stage;

import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StageDao extends JpaRepository<Stage, Integer> {

    Stage getById(int id);

    Stage getByName(String name);

    Stage getByBatchIdAndIndex(int batchId, int index);

    List<Stage> getAllByBatchId(int batchId);

    List<Stage> getAllByBatchIdAndState(int batchId, StageState state);

    List<Stage> getAllByBatchIdAndIndexGreaterThan(int batchId, int index);

    List<Stage> getAllByBatchIdAndIndexStartingWith(int batchId, int index);

    List<Stage> getAllByBatchIdAndIndexLessThan(int batchId, int index);

    boolean existsByBatchId(int batchId);

    int countByBatchId(int batchId);

    boolean existsByBatchIdAndIndex(int batchId, int i);

    void deleteAllByBatchId(int batchId);

    void deleteByBatchIdAndIndex(int batchId, int index);
}
