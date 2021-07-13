package com.prodnees.shelf.dao;

import com.prodnees.core.dao.queries.QueryConstants;
import com.prodnees.shelf.domain.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RawProductDao extends JpaRepository<RawMaterial, Integer> {

    RawMaterial getById(int id);

    RawMaterial getByName(String name);

    @Query(nativeQuery = true, value = QueryConstants.RAW_PRODUCT_DAO_GET_ALL_BY_STAGE_ID)
    List<RawMaterial> getAllByStageId(int id);
}
