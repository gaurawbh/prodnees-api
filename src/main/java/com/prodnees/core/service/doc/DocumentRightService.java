package com.prodnees.core.service.doc;

import com.prodnees.core.domain.doc.UserDocumentRight;

import java.util.List;
import java.util.Optional;

public interface DocumentRightService {
    UserDocumentRight save(UserDocumentRight userDocumentRight);

    Optional<UserDocumentRight> findByDocumentIdAndUserId(int documentId, int userId);

    boolean existsByDocumentIdAndUserId(int documentId, int userId);

    List<UserDocumentRight> getAllByDocumentId(int documentId);

    List<UserDocumentRight> getAllByUserId(int userId);

    boolean hasEditRights(UserDocumentRight userDocumentRight);

}
