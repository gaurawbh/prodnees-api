package com.prodnees.core.dao.batchproduct;

import com.prodnees.core.dao.queries.QueryConstants;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchDao extends JpaRepository<Batch, Integer> {
    boolean existsByIdAndState(int id, BatchState state);

    Batch getById(int id);

    List<Batch> getAllByProductId(int productId);

    @Query(nativeQuery = true, value = "select id, name from batch")
    List<Batch> getAllIdAndName();

    @Query(nativeQuery = true, value = QueryConstants.BATCH_DAO_GET_ALL_BY_USER_ID_AND_STATE)
    List<Batch> getAllByUserIdAndState(int userId, String state);

    @Query(nativeQuery = true, value = QueryConstants.GLOBAL_GET_NEXT_ID)
    int getNextId(String tableSchema, String tableName, String columnName);

}
