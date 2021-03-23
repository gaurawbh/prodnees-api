package com.prodnees.service;

import com.prodnees.domain.Event;

import java.util.List;

public interface EventService {

    boolean existsByBatchProductId(int batchProductId);

    Event save(Event event);

    Event getById(int id);

    List<Event> getAllByBatchProductId(int batchProductId);

    List<Event> getAllByStateId(int stateId);

    Event getByBatchProductIdAndName(int batchProductId, String name);


}
