package com.prodnees.service.impl;

import com.prodnees.dao.DocumentDao;
import com.prodnees.domain.Document;
import com.prodnees.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
    private final DocumentDao documentDao;

    public DocumentServiceImpl(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }

    @Override
    public Document save(Document document) {
        return documentDao.save(document);
    }

    @Override
    public Document getById(int id) {
        return null;
    }
}
