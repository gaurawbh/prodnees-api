package com.prodnees.core.dao.batchproduct;

import com.prodnees.core.dao.queries.QueryConstants;
import com.prodnees.core.domain.batch.RawProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawProductDao extends JpaRepository<RawProduct, Integer> {

    RawProduct getById(int id);

    RawProduct getByName(String name);

    @Query(nativeQuery = true, value = QueryConstants.RAW_PRODUCT_DAO_GET_ALL_BY_STAGE_ID)
    List<RawProduct> getAllByStageId(int id);
}
