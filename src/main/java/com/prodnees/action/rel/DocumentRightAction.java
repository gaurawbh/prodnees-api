package com.prodnees.action.rel;

import com.prodnees.domain.rels.DocumentRight;

import java.util.List;
import java.util.Optional;

public interface DocumentRightAction {

    DocumentRight addNew(DocumentRight documentRight);

    Optional<DocumentRight> findByDocumentIdAndUserId(int documentId, int userId);

    boolean existsByDocumentIdAndUserId(int documentId, int userId);

    List<DocumentRight> getAllByDocumentId(int documentId);

    List<DocumentRight> getAllByUserId(int userId);

    boolean hasEditRights(DocumentRight documentRight);
}
