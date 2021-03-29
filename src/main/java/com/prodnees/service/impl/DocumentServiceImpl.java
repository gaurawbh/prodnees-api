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
        return documentDao.getById(id);
    }

    @Override
    public Document getByName(String name) {
        return documentDao.getByName(name);
    }

    @Override
    public boolean existsByName(String name) {
        return documentDao.existsByName(name);
    }

    @Override
    public boolean existsById(int id) {
        return documentDao.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        documentDao.deleteById(id);
    }
}
