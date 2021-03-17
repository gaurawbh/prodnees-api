package com.prodnees.domain.rels;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

@Entity
@IdClass(BatchProductApprovalDocument.class)
public class BatchProductApprovalDocument implements Serializable {
    @Id
    private int batchProductId;
    @Id
    private int approvalDocumentId;

    public int getBatchProductId() {
        return batchProductId;
    }

    public BatchProductApprovalDocument setBatchProductId(int batchProductId) {
        this.batchProductId = batchProductId;
        return this;
    }

    public int getApprovalDocumentId() {
        return approvalDocumentId;
    }

    public BatchProductApprovalDocument setApprovalDocumentId(int approvalDocumentId) {
        this.approvalDocumentId = approvalDocumentId;
        return this;
    }
}
