package com.prodnees.service;

import com.prodnees.domain.Event;
import org.springframework.stereotype.Service;

import java.util.List;
public interface EventService {

    Event save(Event event);

    Event getById(int id);

    List<Event> getAllByBatchProductId(int batchProductId);

    List<Event> getAllByStateId(int batchProductId);


}
