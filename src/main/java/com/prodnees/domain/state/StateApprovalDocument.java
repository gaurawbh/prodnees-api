package com.prodnees.domain.state;

import com.prodnees.domain.enums.ApprovalDocumentState;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class StateApprovalDocument {

    @Id
    @GeneratedValue
    private int id;
    private int stateId;
    private int documentId;
    private String name;
    @Enumerated(EnumType.STRING)
    private ApprovalDocumentState state;
    private int approverId;
    private String approverEmail;

    public int getId() {
        return id;
    }

    public StateApprovalDocument setId(int id) {
        this.id = id;
        return this;
    }

    public int getStateId() {
        return stateId;
    }

    public StateApprovalDocument setStateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public int getDocumentId() {
        return documentId;
    }

    public StateApprovalDocument setDocumentId(int documentId) {
        this.documentId = documentId;
        return this;
    }

    public ApprovalDocumentState getState() {
        return state;
    }

    public StateApprovalDocument setState(ApprovalDocumentState state) {
        this.state = state;
        return this;
    }

    public int getApproverId() {
        return approverId;
    }

    public StateApprovalDocument setApproverId(int approverId) {
        this.approverId = approverId;
        return this;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public StateApprovalDocument setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }

    public String getName() {
        return name;
    }

    public StateApprovalDocument setName(String name) {
        this.name = name;
        return this;
    }
}
