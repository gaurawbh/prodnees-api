package com.prodnees.core.service.rels;

import com.prodnees.core.domain.rels.DocumentRight;

import java.util.List;
import java.util.Optional;

public interface DocumentRightService {
    DocumentRight save(DocumentRight documentRight);

    Optional<DocumentRight> findByDocumentIdAndUserId(int documentId, int userId);

    boolean existsByDocumentIdAndUserId(int documentId, int userId);

    List<DocumentRight> getAllByDocumentId(int documentId);

    List<DocumentRight> getAllByUserId(int userId);

    boolean hasEditRights(DocumentRight documentRight);

}
