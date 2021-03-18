package com.prodnees.dao;

import com.prodnees.domain.ApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalDocumentDao extends JpaRepository<ApprovalDocument, Integer> {
    ApprovalDocument getById(int id);

    List<ApprovalDocument> getAllByDocumentId(int documentId);
}
