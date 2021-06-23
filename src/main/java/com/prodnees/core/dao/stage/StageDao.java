package com.prodnees.core.dao.stage;

import com.prodnees.core.dao.queries.QueryConstants;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StageDao extends JpaRepository<Stage, Integer> {

    Stage getById(int id);

    Stage getByName(String name);

    Stage getByBatchIdAndIndx(int batchId, int index);

    List<Stage> getAllByBatchId(int batchId);

    List<Stage> getAllByBatchIdAndState(int batchId, StageState state);

    List<Stage> getAllByBatchIdAndIndxGreaterThan(int batchId, int index);

    List<Stage> getAllByBatchIdAndIndxGreaterThanEqual(int batchId, int index);

    List<Stage> getAllByBatchIdAndIndxLessThan(int batchId, int index);

    boolean existsByBatchId(int batchId);

    int countByBatchId(int batchId);

    boolean existsByBatchIdAndIndx(int batchId, int i);

    void deleteAllByBatchId(int batchId);

    void deleteByBatchIdAndIndx(int batchId, int index);

    @Query(nativeQuery = true, value = QueryConstants.GLOBAL_GET_NEXT_ID)
    int getNextId(String tableSchema, String tableName, String columnName);
}
