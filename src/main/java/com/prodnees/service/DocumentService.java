package com.prodnees.service;

import com.prodnees.domain.Document;

public interface DocumentService {

    Document save(Document document);

    Document getById(int id);

}
