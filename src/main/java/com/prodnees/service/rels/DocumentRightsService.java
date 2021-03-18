package com.prodnees.service.rels;

import com.prodnees.domain.rels.DocumentRights;

import java.util.List;
import java.util.Optional;

public interface DocumentRightsService {
    DocumentRights save(DocumentRights documentRights);

    Optional<DocumentRights> findByDocumentIdAndUserId(int documentId, int userId);

    List<DocumentRights> getAllByDocumentId(int documentId);

    List<DocumentRights> getAllByUserId(int userId);
}
