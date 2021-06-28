package com.prodnees.core.action.rel;

import com.prodnees.core.domain.doc.UserDocumentRight;

import java.util.List;
import java.util.Optional;

public interface DocumentRightAction {

    UserDocumentRight addNew(UserDocumentRight userDocumentRight);

    Optional<UserDocumentRight> findByDocumentIdAndUserId(int documentId, int userId);

    boolean existsByDocumentIdAndUserId(int documentId, int userId);

    List<UserDocumentRight> getAllByDocumentId(int documentId);

    List<UserDocumentRight> getAllByUserId(int userId);

    boolean hasEditRights(UserDocumentRight userDocumentRight);
}
