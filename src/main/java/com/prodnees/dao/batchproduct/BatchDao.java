package com.prodnees.dao.batchproduct;

import com.prodnees.dao.queries.QueryConstants;
import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BatchDao extends JpaRepository<Batch, Integer> {
    boolean existsByIdAndStatus(int id, BatchStatus status);

    Batch getById(int id);

    List<Batch> getAllByProductId(int productId);

    @Query(nativeQuery = true, value = QueryConstants.BATCH_DAO_GET_ALL_BY_USER_ID_AND_STATUS)
    List<Batch> getAllByUserIdAndStatus(int userId, String status);

}
