package com.prodnees.service;

import com.prodnees.dao.queries.QueryConstants;
import com.prodnees.domain.Document;
import org.springframework.data.jpa.repository.Query;

public interface DocumentService {

    Document save(Document document);

    Document getById(int id);

    Document getByName(String name);

    boolean existsByName(String name);

    boolean existsById(int id);

    void deleteById(int id);

    int getNextId();

}
