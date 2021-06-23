package com.prodnees.core.service.impl;

import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.core.dao.DocumentDao;
import com.prodnees.core.domain.Document;
import com.prodnees.core.service.DocumentService;
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

    @Override
    public int getNextId() {
        return documentDao.getNextId(CurrentTenantResolver.getTenant(), "document", "id");
    }
}
