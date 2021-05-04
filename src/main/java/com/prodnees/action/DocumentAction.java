package com.prodnees.action;

import com.prodnees.domain.Document;
import com.prodnees.model.DocumentModel;

import java.util.List;

public interface DocumentAction {

    DocumentModel save(Document document);

    Document getById(int id);

    DocumentModel getModelById(int id);

    DocumentModel getByName(String name);

    List<DocumentModel> getAllByUserId(int userId);

    boolean existsByName(String name);

    void deleteById(int id);
}
