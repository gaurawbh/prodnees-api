package com.prodnees.core.action.rel.impl;

import com.prodnees.core.action.rel.DocumentRightAction;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.DocumentRight;
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
    public DocumentRight addNew(DocumentRight documentRight) {
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
