package com.prodnees.service;

import com.prodnees.domain.ApprovalDocument;
import com.prodnees.domain.DocumentState;

import java.util.List;

public interface ApprovalDocumentService {

    ApprovalDocument save(ApprovalDocument approvalDocument);

    ApprovalDocument getById(int id);

    List<ApprovalDocument> getAllByBatchProductId(int batchProductId);

    List<ApprovalDocument> getAllByStateId(int stateId);

    List<ApprovalDocument> getAllByStateIdAndDocumentState(int stateId, DocumentState documentState);

    List<ApprovalDocument> getAllByBatchProductIdAndDocumentState(int stateId, DocumentState documentState);

}
