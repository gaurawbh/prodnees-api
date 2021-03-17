package com.prodnees.model;

import com.prodnees.domain.Document;
import com.prodnees.domain.DocumentState;
import com.prodnees.domain.User;
import java.util.List;

/**
 * Document that needs to be approved
 * Used in States where a State may not be complete until a Document has been approved
 */
public class ApprovalDocumentModel {
    private int id;
    private BatchProductModel batchProduct;
    private StateModel state;
    private Document document;
    private DocumentState documentState;
    private List<User> approvers;



}
