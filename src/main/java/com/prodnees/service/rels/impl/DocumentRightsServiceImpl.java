package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.DocumentRightsDao;
import com.prodnees.domain.rels.DocumentRights;
import com.prodnees.service.rels.DocumentRightsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DocumentRightsServiceImpl implements DocumentRightsService {

    private final DocumentRightsDao documentRightsDao;

    public DocumentRightsServiceImpl(DocumentRightsDao documentRightsDao) {
        this.documentRightsDao = documentRightsDao;
    }

    @Override
    public DocumentRights save(DocumentRights documentRights) {
        return documentRightsDao.save(documentRights);
    }

    @Override
    public Optional<DocumentRights> findByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightsDao.findByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public List<DocumentRights> getAllByDocumentId(int documentId) {
        return documentRightsDao.getAllByDocumentId(documentId);
    }

    @Override
    public List<DocumentRights> getAllByUserId(int userId) {
        return documentRightsDao.getAllByUserId(userId);
    }
}
