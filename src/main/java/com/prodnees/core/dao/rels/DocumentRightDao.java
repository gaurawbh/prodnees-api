package com.prodnees.core.dao.rels;

import com.prodnees.core.domain.rels.DocumentRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRightDao extends JpaRepository<DocumentRight, Integer> {
    Optional<DocumentRight> findByDocumentIdAndUserId(int documentId, int ownerId);

    List<DocumentRight> getAllByDocumentId(int documentId);

    List<DocumentRight> getAllByUserId(int ownerId);

    boolean existsByDocumentIdAndUserId(int documentId, int userId);
}
