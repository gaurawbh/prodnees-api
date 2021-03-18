package com.prodnees.dao.rels;

import com.prodnees.domain.rels.DocumentRights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRightsDao extends JpaRepository<DocumentRights, Integer> {
    Optional<DocumentRights> findByDocumentIdAndUserId(int documentId, int ownerId);

    List<DocumentRights> getAllByDocumentId(int documentId);

    List<DocumentRights> getAllByUserId(int ownerId);
}
