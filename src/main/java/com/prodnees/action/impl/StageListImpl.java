package com.prodnees.action.impl;

import com.prodnees.action.StageList;
import com.prodnees.auth.config.tenancy.CurrentTenantResolver;
import com.prodnees.dao.stage.StageDao;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.stage.Stage;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * {@link StageList} is a stack of {@link Stage}s and one StageList belongs to one {@link Batch}.
 * <p> Each Stage has an index starting with 0. When a new Stage is added to the {@link StageList}, and the Stage has a valid index value, i.e. >=0, the Stage will be place in that index.</p>
 *
 * <P>If a Stage does not have a valid index value, i.e. >=0, the the Stage will be placed on the top of the {@link StageList}</P>
 *
 * <P>Example, if the {@link StageList} has 4 Stages -></p>
 * <p>Stage-1[index=0]</p>
 * <p>Stage-2[index=1]</p>
 * <p>Stage-3[index=2]</p>
 * <p>Stage-4[index=3]</p>
 * <p> and a new {@link Stage} <u> Stage-x[index-2] </u> is added with a valid index, 2,The  {@link StageList} will not have  5 Stages:  </P>
 * <p>Stage-1[index=0]</p>
 * <p>Stage-2[index=1]</p>
 * <p><u> Stage-x[index=2] </u></p>
 * <p>Stage-3[index=3]</p>
 * <p>Stage-4[index=4]</p>
 */

@Service
public class StageListImpl implements StageList {
    private static final String STATE_INDEX_OUT_OF_BOUND = "total states in batch with id: %d is out of bounds for index: %d";

    private final StageDao stageDao;

    public StageListImpl(StageDao stageDao) {
        this.stageDao = stageDao;
    }

    @Override
    public Stage getFirst(int batchId) {
        return stageDao.getByBatchIdAndIndx(batchId, 0);
    }

    @Override
    public Stage get(int batchId, int index) {
        int batchSize = size(batchId);
        if (index >= batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        return stageDao.getByBatchIdAndIndx(batchId, index);
    }

    /**
     * Top of the {@link StageList}, highest index
     * @param batchId
     * @return
     */
    @Override
    public Stage getLast(int batchId) {
        int batchSize = size(batchId);
        return stageDao.getByBatchIdAndIndx(batchId, batchSize - 1);
    }

    /**
     * Retrieve all Stages of a batch and delete the one with the lowest index and decrement index by 1 of the rest of the Stages
     * @param batchId
     * @return
     */
    @Override
    public boolean removeFirst(int batchId) {
        if (isEmpty(batchId)) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, 0, 0));
        }
        List<Stage> stageList = stageDao.getAllByBatchId(batchId);
        stageList.sort(Comparator.comparing(Stage::getIndx));
        stageDao.deleteById(stageList.get(0).getId());
        stageList.remove(0);
        stageList.forEach(state -> state.setIndx(state.getIndx() - 1));
        stageDao.saveAll(stageList);
        return true;
    }

    /**
     * Retrieve all Stages of a batch and delete the one with the highest index
     * @param batchId
     * @return
     */
    @Override
    public boolean removeLast(int batchId) {
        if (isEmpty(batchId)) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, 0, 0));
        }
        List<Stage> stageList = stageDao.getAllByBatchId(batchId);
        stageList.sort(Comparator.comparing(Stage::getIndx).reversed());
        stageDao.deleteById(stageList.get(0).getId());
        return true;
    }

    /**
     * total count of {@link Stage} in a {@link Batch}
     * @param batchId
     * @return
     */
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
        return stageDao.existsByBatchIdAndIndx(batchId, index + 1);
    }

    /**
     * If a new {@link Stage} is added and it does not have a valid index <u>>=0</u>, it will be added to the top of the stack
     * @param stage
     * @return
     */
    @Override
    public Stage add(Stage stage) {
        if (stage.getIndx() >= 0) {
            return add(stage, stage.getIndx());
        } else {
            stage.setIndx(size(stage.getBatchId()));
            return stageDao.save(stage);
        }
    }

    /**
     * If a new Stage has a valid index <U>>=0</U>, it will be added to that index and all the other Stages after that Stage will have their indexes incremented by 1
     * @param stage
     * @param index
     * @return
     */
    @Override
    public Stage add(Stage stage, int index) {
        int batchSize = size(stage.getBatchId());
        if (index > batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        List<Stage> stageList = stageDao.getAllByBatchIdAndIndxStartingWith(stage.getBatchId(), index);
        stageList.forEach(state1 -> state1.setIndx(stage.getIndx() + 1));
        stageList.add(stage);
        stageDao.saveAll(stageList);
        return stageDao.getByBatchIdAndIndx(stage.getBatchId(), index);
    }
   /**
     * If a  Stage is removed from the {@link StageList}, all Stages above that removed Stage will have their indexes decremented by 1
     * @param stage
     * @return
     */
    @Override
    public void remove(Stage stage) {
        List<Stage> stageList = stageDao.getAllByBatchIdAndIndxGreaterThan(stage.getBatchId(), stage.getIndx());
        stageList.forEach(state1 -> state1.setIndx(state1.getIndx() - 1)); // decrement index of the Stages on top of the Stage to be removed
        stageDao.deleteById(stage.getId());
        stageDao.saveAll(stageList);
    }

    /**
     * Delete at index, not by Stage's id
     * @param batchId
     * @param index
     */
    @Override
    public void remove(int batchId, int index) {
        int batchSize = size(batchId);
        if (index >= batchSize) {
            throw new IndexOutOfBoundsException(String.format(STATE_INDEX_OUT_OF_BOUND, batchSize, index));
        }
        stageDao.deleteByBatchIdAndIndx(batchId, index);
        List<Stage> stageList = stageDao.getAllByBatchIdAndIndxGreaterThan(batchId, index);
        stageList.forEach(state -> state.setIndx(state.getIndx() - 1));
        stageDao.saveAll(stageList);
    }

    @Override
    public List<Stage> addAll(int batchId, List<Stage> stageList) {
        int size = size(batchId);
        for (Stage stage : stageList) {
            stage.setIndx(size);
            size++;
        }
        return stageDao.saveAll(stageList);
    }

    @Override
    public boolean clear(int batchId) {
        stageDao.deleteAllByBatchId(batchId);
        return true;
    }

    @Override
    public int getNextId() {
        return stageDao.getNextId(CurrentTenantResolver.getTenant(), "stage", "id");
    }

}
