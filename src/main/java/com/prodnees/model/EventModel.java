package com.prodnees.model;

/**
 * An Event that may be happening during a State of Product Batch
 * It can either be constrained by time, or other things
 */
public class EventModel {
    private int id;
    private BatchProductModel batchProduct;
    private StateModel state;
    private String name;
    private String description;
    private boolean complete;



}
