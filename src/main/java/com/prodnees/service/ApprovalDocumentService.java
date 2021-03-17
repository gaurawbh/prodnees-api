package com.prodnees.service;

import com.prodnees.domain.ApprovalDocument;

import java.util.List;

public interface ApprovalDocumentService {

    ApprovalDocument save(ApprovalDocument approvalDocument);

    ApprovalDocument getById(int id);
    List<ApprovalDocument> getAllByDocumentId(int documentId);

}
