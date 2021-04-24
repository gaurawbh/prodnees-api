package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.DocumentRightDao;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.service.rels.DocumentRightService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentRightServiceImpl implements DocumentRightService {

    private final DocumentRightDao documentRightDao;

    public DocumentRightServiceImpl(DocumentRightDao documentRightDao) {
        this.documentRightDao = documentRightDao;
    }

    @Override
    public DocumentRight save(DocumentRight documentRight) {
        return documentRightDao.save(documentRight);
    }

    @Override
    public Optional<DocumentRight> findByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightDao.findByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public boolean existsByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightDao.existsByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public List<DocumentRight> getAllByDocumentId(int documentId) {
        return documentRightDao.getAllByDocumentId(documentId);
    }

    @Override
    public List<DocumentRight> getAllByUserId(int userId) {
        return documentRightDao.getAllByUserId(userId);
    }
}
