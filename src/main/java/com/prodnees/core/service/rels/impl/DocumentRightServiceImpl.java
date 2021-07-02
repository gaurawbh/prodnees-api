package com.prodnees.core.service.rels.impl;

import com.prodnees.core.dao.doc.DocumentRightDao;
import com.prodnees.core.domain.doc.DocumentPermission;
import com.prodnees.core.domain.doc.UserDocumentRight;
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
    public UserDocumentRight save(UserDocumentRight userDocumentRight) {
        return documentRightDao.saveAndFlush(userDocumentRight);
    }

    @Override
    public Optional<UserDocumentRight> findByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightDao.findByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public boolean existsByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightDao.existsByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public List<UserDocumentRight> getAllByDocumentId(int documentId) {
        return documentRightDao.getAllByDocumentId(documentId);
    }

    @Override
    public List<UserDocumentRight> getAllByUserId(int userId) {
        return documentRightDao.getAllByUserId(userId);
    }


    @Override
    public boolean hasEditRights(UserDocumentRight userDocumentRight) {
        return userDocumentRight.getDocumentPermission() == DocumentPermission.Delete
                || userDocumentRight.getDocumentPermission() == DocumentPermission.Edit;
    }
}
