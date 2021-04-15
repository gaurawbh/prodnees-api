package com.prodnees.dao.queries;

import com.prodnees.dao.batchproduct.BatchDao;

/**
 * Naming convention:
 * <p>variable name should start with DAO class name it is being called from</p>
 * <p>ie</p>
 * <p>{@link QueryConstants#BATCH_DAO_GET_ALL_BY_USER_ID_AND_STATE} is being called from {@link BatchDao} so the variable name starts with: </p>
 * <i> BATCH_DAO </i>
 */
public class QueryConstants {
    public static final String BATCH_DAO_GET_ALL_BY_USER_ID_AND_STATE = "select * from batch where id in (select batch_id from batch_right where user_id = ?1) and state = ?2";
    public static final String RAW_PRODUCT_DAO_GET_ALL_BY_STAGE_ID = "select * from raw_product where id in(select raw_product_id from stage_raw_product where stage_id = 1?)";
    private QueryConstants() {
    }
}
