package com.prodnees.core.dao.doc;

import com.prodnees.core.domain.doc.UserDocumentRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRightDao extends JpaRepository<UserDocumentRight, Integer> {
    Optional<UserDocumentRight> findByDocumentIdAndUserId(int documentId, int ownerId);

    List<UserDocumentRight> getAllByDocumentId(int documentId);

    List<UserDocumentRight> getAllByUserId(int ownerId);

    boolean existsByDocumentIdAndUserId(int documentId, int userId);
}
