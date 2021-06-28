package com.prodnees.core.service;

import com.prodnees.core.domain.NeesDoc;

public interface NeesDocumentService {

    NeesDoc save(NeesDoc neesDoc);

    NeesDoc getById(int id);

    NeesDoc getByName(String name);

    boolean existsByName(String name);

    boolean existsById(int id);

    void deleteById(int id);

    int getNextId();

}
