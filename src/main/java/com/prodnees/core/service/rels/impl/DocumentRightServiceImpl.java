package com.prodnees.core.service.rels.impl;

import com.prodnees.core.dao.rels.DocumentRightDao;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.DocumentRight;
import com.prodnees.core.service.rels.DocumentRightService;
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


    @Override
    public boolean hasEditRights(DocumentRight documentRight) {
        return documentRight.getDocumentRightsType() == ObjectRight.OWNER
                || documentRight.getDocumentRightsType() == ObjectRight.EDITOR;
    }
}
