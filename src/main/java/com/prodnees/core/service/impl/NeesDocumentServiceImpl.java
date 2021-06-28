package com.prodnees.core.service.impl;

import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.core.dao.NeesDocDao;
import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.service.NeesDocumentService;
import org.springframework.stereotype.Service;

@Service
public class NeesDocumentServiceImpl implements NeesDocumentService {
    private final NeesDocDao neesDocDao;

    public NeesDocumentServiceImpl(NeesDocDao neesDocDao) {
        this.neesDocDao = neesDocDao;
    }

    @Override
    public NeesDoc save(NeesDoc neesDoc) {
        return neesDocDao.save(neesDoc);
    }

    @Override
    public NeesDoc getById(int id) {
        return neesDocDao.getById(id);
    }

    @Override
    public NeesDoc getByName(String name) {
        return neesDocDao.getByName(name);
    }

    @Override
    public boolean existsByName(String name) {
        return neesDocDao.existsByName(name);
    }

    @Override
    public boolean existsById(int id) {
        return neesDocDao.existsById(id);
    }

    @Override
    public void deleteById(int id) {
        neesDocDao.deleteById(id);
    }

    @Override
    public int getNextId() {
        return neesDocDao.getNextId(CurrentTenantResolver.getTenant(), "document", "id");
    }
}
