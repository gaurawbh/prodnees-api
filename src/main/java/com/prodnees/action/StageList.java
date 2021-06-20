package com.prodnees.action;

import com.prodnees.domain.stage.Stage;

import java.util.List;

/**
 * All {@link com.prodnees.domain.rels.BatchRight} must be checked before using this class
 */
public interface StageList {

    Stage getFirst(int batchId);

    Stage get(int batchId, int index);

    Stage getLast(int batchId);

    boolean removeFirst(int batchId);

    boolean removeLast(int batchId);

    int size(int batchId);

    boolean isEmpty(int batchId);

    boolean hasNext(int batchId, int index);

    Stage add(Stage stage);

    Stage add(Stage stage, int index);

    void remove(Stage stage);

    void remove(int batchId, int index);

    List<Stage> addAll(int batchId, List<Stage> stageList);

    boolean clear(int batchId);

    int getNextId();

}
