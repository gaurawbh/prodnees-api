package com.prodnees.service;

import com.prodnees.domain.Document;

import javax.print.Doc;

public interface DocumentService {

    Document save(Document document);

    Document getById(int id);

    Document getByName(String name);

}
