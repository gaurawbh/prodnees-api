package com.prodnees.action.impl;

import com.prodnees.action.StateAction;
import com.prodnees.domain.State;
import com.prodnees.dto.StateDto;
import com.prodnees.service.StateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StateActionImpl implements StateAction {

    private final StateService stateService;

    public StateActionImpl(StateService stateService) {
        this.stateService = stateService;
    }

    @Override
    public boolean existsByBatchProductId(int batchProductId) {
        return stateService.existsByBatchProductId(batchProductId);
    }

    @Override
    public State save(State state) {
        return stateService.save(state);
    }

    @Override
    public State save(StateDto stateDto) {
        return null;
    }

    @Override
    public State getById(int id) {
        return stateService.getById(id);
    }

    @Override
    public State getByName(String name) {
        return stateService.getByName(name);
    }

    @Override
    public List<State> getAllByBatchProductId(int batchProductId) {
        return stateService.getAllByBatchProductId(batchProductId);
    }

    @Override
    public List<State> getAllByBatchProductIdAndComplete(int batchProductId, boolean isComplete) {
        return stateService.getAllByBatchProductIdAndComplete(batchProductId, isComplete);
    }

    @Override
    public void deleteById(int id) {
        State state = stateService.getById(id);
        Optional<State> tailStateOpt = Optional.ofNullable(stateService.getById(state.getLastStateId()));
        Optional<State> headStateOpt = Optional.ofNullable(stateService.getById(state.getNextStateId()));
        if (tailStateOpt.isPresent() && headStateOpt.isPresent()) {
            tailStateOpt.get().setNextStateId(headStateOpt.get().getId());
            stateService.save(tailStateOpt.get());
            headStateOpt.get().setLastStateId(tailStateOpt.get().getId());
            stateService.save(headStateOpt.get());
        }
        stateService.deleteById(id);
    }
}
