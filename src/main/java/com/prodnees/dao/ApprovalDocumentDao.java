package com.prodnees.dao;

import com.prodnees.domain.ApprovalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalDocumentDao extends JpaRepository<ApprovalDocument, Integer> {
}
