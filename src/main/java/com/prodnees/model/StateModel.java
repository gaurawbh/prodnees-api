package com.prodnees.model;

import com.prodnees.domain.RawProduct;
import com.prodnees.domain.User;
import java.util.List;

/**
 * Every Product Batch has a state.
 * A Product Batch must have at least two states:
 * * Initial State [A Batch Production Start Point]
 * * Final State [A Batch Production Has Completed]
 */
public class StateModel {
    private int id;
    private int batchProductId;
    private String name;
    private String description;
    List<ApprovalDocumentModel> approvalDocuments;
    List<User> approvers;
    List<EventModel> events;
    List<RawProduct> rawProducts;
    private boolean complete;
    private StateModel lastState;
    private StateModel nextState;

}
