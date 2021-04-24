package com.prodnees.domain.stage;

import com.prodnees.domain.enums.ApprovalDocumentState;

import javax.persistence.*;

@Entity
public class StageApprovalDocument {

    @Id
    @GeneratedValue
    private int id;
    private int stageId;
    private int documentId;
    @Enumerated(EnumType.STRING)
    private ApprovalDocumentState state;
    private int approverId;
    private String approverEmail;

    public int getId() {
        return id;
    }

    public StageApprovalDocument setId(int id) {
        this.id = id;
        return this;
    }

    public int getStageId() {
        return stageId;
    }

    public StageApprovalDocument setStageId(int stateId) {
        this.stageId = stateId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public StageApprovalDocument setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ApprovalDocumentState getState() {
        return state;
    }

    public StageApprovalDocument setState(ApprovalDocumentState state) {
        this.state = state;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public StageApprovalDocument setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public StageApprovalDocument setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }

}
