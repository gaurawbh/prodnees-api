package com.prodnees.core.service;

import com.prodnees.core.domain.Document;

public interface DocumentService {

    Document save(Document document);

    Document getById(int id);

    Document getByName(String name);

    boolean existsByName(String name);

    boolean existsById(int id);

    void deleteById(int id);

    int getNextId();

}
