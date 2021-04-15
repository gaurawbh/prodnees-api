package com.prodnees.action.impl;

import com.prodnees.action.BatchStageList;
import com.prodnees.dao.stage.StageDao;
import com.prodnees.domain.stage.Stage;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;


@Service
public class BatchStageListImpl implements BatchStageList {
    private static final String STATE_INDEX_OUT_OF_BOUND = "total states in batch with id: %d is out of bounds for index: %d";

    private final StageDao stageDao;

    public BatchStageListImpl(StageDao stageDao) {
        this.stageDao = stageDao;
    }

    @Override
    public Stage getFirst(int batchId) {
        return stageDao.getByBatchIdAndIndex(batchId, 0);
    }

    @Override
    public Stage get(int batchId, int index) {
        int batchSize = size(batchId);
        if (index >= batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        return stageDao.getByBatchIdAndIndex(batchId, index);
    }

    @Override
    public Stage getLast(int batchId) {
        int batchSize = size(batchId);
        return stageDao.getByBatchIdAndIndex(batchId, batchSize - 1);
    }

    @Override
    public boolean removeFirst(int batchId) {
        if (isEmpty(batchId)) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, 0, 0));
        }
        List<Stage> stageList = stageDao.getAllByBatchId(batchId);
        stageList.sort(Comparator.comparing(Stage::getIndex));
        stageDao.deleteById(stageList.get(0).getId());
        stageList.remove(0);
        stageList.forEach(state -> state.setIndex(state.getIndex() - 1));
        stageDao.saveAll(stageList);
        return true;
    }

    @Override
    public boolean removeLast(int batchId) {
        if (isEmpty(batchId)) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, 0, 0));
        }
        List<Stage> stageList = stageDao.getAllByBatchId(batchId);
        stageList.sort(Comparator.comparing(Stage::getIndex).reversed());
        stageDao.deleteById(stageList.get(0).getId());
        return true;
    }

    @Override
    public int size(int batchId) {
        return stageDao.countByBatchId(batchId);
    }

    @Override
    public boolean isEmpty(int batchId) {
        return stageDao.existsByBatchId(batchId);
    }

    @Override
    public boolean hasNext(int batchId, int index) {
        return stageDao.existsByBatchIdAndIndex(batchId, index + 1);
    }

    @Override
    public Stage add(Stage stage) {
        if (stage.getIndex() >= 0) {
            return add(stage, stage.getIndex());
        } else {
            stage.setIndex(size(stage.getBatchId()));
            return stageDao.save(stage);
        }
    }

    @Override
    public Stage add(Stage stage, int index) {
        int batchSize = size(stage.getBatchId());
        if (index > batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        List<Stage> stageList = stageDao.getAllByBatchIdAndIndexStartingWith(stage.getBatchId(), index);
        stageList.forEach(state1 -> state1.setIndex(stage.getIndex() + 1));
        stageList.add(stage);
        stageDao.saveAll(stageList);
        return stageDao.getByBatchIdAndIndex(stage.getBatchId(), index);
    }

    @Override
    public void remove(Stage stage) {
        List<Stage> stageList = stageDao.getAllByBatchIdAndIndexGreaterThan(stage.getBatchId(), stage.getIndex());
        stageList.forEach(state1 -> state1.setIndex(state1.getIndex() - 1));
        stageDao.deleteById(stage.getId());
        stageDao.saveAll(stageList);
    }

    @Override
    public void remove(int batchId, int index) {
        int batchSize = size(batchId);
        if (index >= batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        stageDao.deleteByBatchIdAndIndex(batchId, index);
        List<Stage> stageList = stageDao.getAllByBatchIdAndIndexGreaterThan(batchId, index);
        stageList.forEach(state -> state.setIndex(state.getIndex() - 1));
        stageDao.saveAll(stageList);
    }

    @Override
    public List<Stage> addAll(int batchId, List<Stage> stageList) {
        int size = size(batchId);
        for (Stage stage : stageList) {
            stage.setIndex(size);
            size++;
        }
        return stageDao.saveAll(stageList);
    }

    @Override
    public boolean clear(int batchId) {
        stageDao.deleteAllByBatchId(batchId);
        return true;
    }
}
