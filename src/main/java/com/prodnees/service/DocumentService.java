package com.prodnees.service;

import com.prodnees.domain.Document;
import org.springframework.stereotype.Service;

public interface DocumentService {

    Document save(Document document);

    Document getById(int id);

}
