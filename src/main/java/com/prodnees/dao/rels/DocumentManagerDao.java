package com.prodnees.dao.rels;

import com.prodnees.domain.rels.DocumentManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentManagerDao extends JpaRepository<DocumentManager, Integer> {

    Optional<DocumentManager> findByDocumentIdAndManageId(int documentId, int managerId);

    List<DocumentManager> getAllByDocumentId(int documentId);

    List<DocumentManager> getAllByManageId(int managerId);
}
