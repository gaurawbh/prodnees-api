package com.prodnees.dao;

import com.prodnees.domain.RawProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RawProductDao extends JpaRepository<RawProduct, Integer> {
    interface Queries {
        String GET_ALL_BY_STATE_ID = "select * from raw_product where id in(select raw_product_id from state_raw_product where state_id = 1?)";
    }

    RawProduct getById(int id);

    RawProduct getByName(String name);

    @Query(nativeQuery = true, value = Queries.GET_ALL_BY_STATE_ID)
    List<RawProduct> getAllByStateId(int id);
}
