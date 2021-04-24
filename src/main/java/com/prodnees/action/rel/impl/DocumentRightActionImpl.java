package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.DocumentRightAction;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.service.rels.DocumentRightService;
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
    public DocumentRight save(DocumentRight documentRight) {
        return documentRightService.save(documentRight);
    }

    @Override
    public Optional<DocumentRight> findByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightService.findByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public boolean existsByDocumentIdAndUserId(int documentId, int userId) {
        return documentRightService.existsByDocumentIdAndUserId(documentId, userId);
    }

    @Override
    public List<DocumentRight> getAllByDocumentId(int documentId) {
        return documentRightService.getAllByDocumentId(documentId);

    }

    @Override
    public List<DocumentRight> getAllByUserId(int userId) {
        return documentRightService.getAllByUserId(userId);
    }

    @Override
    public boolean hasEditRights(DocumentRight documentRight) {
        return documentRight.getDocumentRightsType() == ObjectRight.OWNER
                || documentRight.getDocumentRightsType() == ObjectRight.EDITOR;
    }

}
