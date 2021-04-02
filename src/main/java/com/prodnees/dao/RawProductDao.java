package com.prodnees.dao;

import com.prodnees.dao.queries.QueryConstants;
import com.prodnees.domain.RawProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RawProductDao extends JpaRepository<RawProduct, Integer> {

    RawProduct getById(int id);

    RawProduct getByName(String name);

    @Query(nativeQuery = true, value = QueryConstants.RAW_PRODUCT_DAO_GET_ALL_BY_STATE_ID)
    List<RawProduct> getAllByStateId(int id);
}
