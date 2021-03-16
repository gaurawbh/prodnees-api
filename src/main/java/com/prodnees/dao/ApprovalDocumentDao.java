package com.prodnees.dao;

import com.prodnees.domain.ApprovalDocument;
import com.prodnees.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalDocumentDao extends JpaRepository<ApprovalDocument, Integer> {
}
