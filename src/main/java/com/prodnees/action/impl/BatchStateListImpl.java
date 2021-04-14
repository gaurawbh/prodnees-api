package com.prodnees.action.impl;

import com.prodnees.action.BatchStateList;
import com.prodnees.dao.state.StateDao;
import com.prodnees.domain.state.State;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;


@Service
public class BatchStateListImpl implements BatchStateList {
    private static final String STATE_INDEX_OUT_OF_BOUND = "total states in batch with id: %d is out of bounds for index: %d";

    private final StateDao stateDao;

    public BatchStateListImpl(StateDao stateDao) {
        this.stateDao = stateDao;
    }

    @Override
    public State getFirst(int batchId) {
        return stateDao.getByBatchIdAndIndex(batchId, 0);
    }

    @Override
    public State get(int batchId, int index) {
        int batchSize = size(batchId);
        if (index >= batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        return stateDao.getByBatchIdAndIndex(batchId, index);
    }

    @Override
    public State getLast(int batchId) {
        int batchSize = size(batchId);
        return stateDao.getByBatchIdAndIndex(batchId, batchSize - 1);
    }

    @Override
    public boolean removeFirst(int batchId) {
        if (isEmpty(batchId)) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, 0, 0));
        }
        List<State> stateList = stateDao.getAllByBatchId(batchId);
        stateList.sort(Comparator.comparing(State::getIndex));
        stateDao.deleteById(stateList.get(0).getId());
        stateList.remove(0);
        stateList.forEach(state -> state.setIndex(state.getIndex() - 1));
        stateDao.saveAll(stateList);
        return true;
    }

    @Override
    public boolean removeLast(int batchId) {
        if (isEmpty(batchId)) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, 0, 0));
        }
        List<State> stateList = stateDao.getAllByBatchId(batchId);
        stateList.sort(Comparator.comparing(State::getIndex).reversed());
        stateDao.deleteById(stateList.get(0).getId());
        return true;
    }

    @Override
    public int size(int batchId) {
        return stateDao.countByBatchId(batchId);
    }

    @Override
    public boolean isEmpty(int batchId) {
        return stateDao.existsByBatchId(batchId);
    }

    @Override
    public boolean hasNext(int batchId, int index) {
        return stateDao.existsByBatchIdAndIndex(batchId, index + 1);
    }

    @Override
    public boolean add(State state) {
        state.setIndex(size(state.getBatchId()));
        stateDao.save(state);
        return true;
    }

    @Override
    public boolean add(State state, int index) {
        int batchSize = size(state.getBatchId());
        if (index > batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        List<State> stateList = stateDao.getAllByBatchIdAndIndexStartingWith(state.getBatchId(), index);
        stateList.forEach(state1 -> state1.setIndex(state.getIndex() + 1));
        stateList.add(state);
        stateDao.saveAll(stateList);
        return true;
    }

    @Override
    public void remove(State state) {
        List<State> stateList = stateDao.getAllByBatchIdAndIndexGreaterThan(state.getBatchId(), state.getIndex());
        stateList.forEach(state1 -> state1.setIndex(state1.getIndex() - 1));
        stateDao.deleteById(state.getId());
        stateDao.saveAll(stateList);
    }

    @Override
    public void remove(int batchId, int index) {
        int batchSize = size(batchId);
        if (index >= batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        stateDao.deleteByBatchIdAndIndex(batchId, index);
        List<State> stateList = stateDao.getAllByBatchIdAndIndexGreaterThan(batchId, index);
        stateList.forEach(state -> state.setIndex(state.getIndex() - 1));
        stateDao.saveAll(stateList);
    }

    @Override
    public List<State> addAll(int batchId, List<State> stateList) {
        int size = size(batchId);
        for (State state : stateList) {
            state.setIndex(size);
            size++;
        }
        return stateDao.saveAll(stateList);
    }

    @Override
    public boolean clear(int batchId) {
        stateDao.deleteAllByBatchId(batchId);
        return true;
    }
}
