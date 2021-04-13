package com.prodnees.action.state.impl;

import com.prodnees.action.state.EventAction;
import com.prodnees.domain.state.Event;
import com.prodnees.service.state.EventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventActionImpl implements EventAction {
    private final EventService eventService;

    public EventActionImpl(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public boolean existsByBatchProductId(int batchProductId) {
        return eventService.existsByBatchProductId(batchProductId);
    }

    @Override
    public Event save(Event event) {
        return eventService.save(event);
    }

    @Override
    public Event getById(int id) {
        return eventService.getById(id);
    }

    @Override
    public List<Event> getAllByBatchProductId(int batchProductId) {
        return eventService.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<Event> getAllByStateId(int stateId) {
        return eventService.getAllByStateId(stateId);
    }

    @Override
    public Event getByBatchProductIdAndName(int batchProductId, String name) {
        return eventService.getByBatchProductIdAndName(batchProductId, name);
    }
}
