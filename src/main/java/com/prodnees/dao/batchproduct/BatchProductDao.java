package com.prodnees.dao.batchproduct;

import com.prodnees.dao.queries.QueryConstants;
import com.prodnees.domain.batchproduct.BatchProduct;
import com.prodnees.domain.enums.BatchProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BatchProductDao extends JpaRepository<BatchProduct, Integer> {
    boolean existsByIdAndStatus(int id, BatchProductStatus status);

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);

    @Query(nativeQuery = true, value = QueryConstants.BATCH_PRODUCT_DAO_GET_ALL_BY_USER_ID_AND_STATUS)
    List<BatchProduct> getAllByUserIdAndStatus(int userId, String status);

}
