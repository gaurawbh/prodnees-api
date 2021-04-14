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
    public boolean existsByBatchId(int batchId) {
        return eventService.existsByBatchId(batchId);
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
    public List<Event> getAllByBatchId(int batchId) {
        return eventService.getAllByBatchId(batchId);
    }

    @Override
    public List<Event> getAllByStateId(int stateId) {
        return eventService.getAllByStateId(stateId);
    }

   }
