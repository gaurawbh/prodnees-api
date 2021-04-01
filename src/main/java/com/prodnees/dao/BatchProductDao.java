package com.prodnees.dao;

import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.BatchProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface BatchProductDao extends JpaRepository<BatchProduct, Integer> {
    boolean existsByIdAndStatus(int id, BatchProductStatus status);

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);

    @Query(nativeQuery = true, value = Queries.GET_ALL_BY_USER_ID_AND_STATUS)
    List<BatchProduct> getAllByUserIdAndStatus(int userId, String status);

    interface Queries {
        String GET_ALL_BY_USER_ID_AND_STATUS = "select * from batch_product where id in (select batch_product_id from batch_product_right where user_id = ?1) and status = ?2";
    }
}
