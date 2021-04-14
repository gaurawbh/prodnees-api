package com.prodnees.action;

import com.prodnees.domain.state.State;
import java.util.List;

/**
 * All {@link com.prodnees.domain.rels.BatchRight} must be checked before using this class
 */
public interface BatchStateList {

    State getFirst(int batchId);

    State get(int batchId, int index);

    State getLast(int batchId);

    boolean removeFirst(int batchId);

    boolean removeLast(int batchId);

    int size(int batchId);

    boolean isEmpty(int batchId);

    boolean hasNext(int batchId, int index);

    boolean add(State state);

    boolean add(State state, int index);

    void remove(State state);

    void remove(int batchId, int index);

    List<State> addAll(int batchId, List<State> stateList);

    boolean clear(int batchId);

}
