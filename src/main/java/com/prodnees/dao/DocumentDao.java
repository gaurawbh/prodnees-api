package com.prodnees.dao;

import com.prodnees.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDao extends JpaRepository<Document, Integer> {

    Document getById(int id);

    Document getByName(String name);

    boolean existsByName(String name);
}
