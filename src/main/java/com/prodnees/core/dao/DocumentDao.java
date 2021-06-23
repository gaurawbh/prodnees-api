package com.prodnees.core.dao;

import com.prodnees.core.dao.queries.QueryConstants;
import com.prodnees.core.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentDao extends JpaRepository<Document, Integer> {

    Document getById(int id);

    Document getByName(String name);

    boolean existsByName(String name);

    @Query(nativeQuery = true, value = QueryConstants.GLOBAL_GET_NEXT_ID)
    int getNextId(String tableSchema, String tableName, String columnName);
}
