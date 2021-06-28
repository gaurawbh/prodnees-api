package com.prodnees.core.action.rel.impl;

import com.prodnees.core.action.rel.DocumentRightAction;
import com.prodnees.core.domain.doc.UserDocumentRight;
import com.prodnees.core.service.rels.DocumentRightService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentRightActionImpl implements DocumentRightAction {
    private final DocumentRightService documentRightService;

    public DocumentRightActionImpl(DocumentRightService documentRightService) {
        this.documentRightService = documentRightService;
    }

    @Override
    public UserDocumentRight addNew(UserDocumentRight userDocumentRight) {
        return documentRightService.save(userDocumentRight);
    }

    @Override
    public Optional<UserDocumentRight> findByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightService.findByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public boolean existsByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightService.existsByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public List<UserDocumentRight> getAllByDocumentId(int documentId) {
        return documentRightService.getAllByDocumentId(documentId);

    }

    @Override
    public List<UserDocumentRight> getAllByUserId(int userId) {
        return documentRightService.getAllByUserId(userId);
    }

    @Override
    public boolean hasEditRights(UserDocumentRight userDocumentRight) {return documentRightService.hasEditRights(userDocumentRight);
    }

}
