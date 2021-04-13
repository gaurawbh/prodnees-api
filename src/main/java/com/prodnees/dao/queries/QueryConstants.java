package com.prodnees.dao.queries;

import com.prodnees.dao.batchproduct.BatchProductDao;

/**
 * Naming convention:
 * <p>variable name should start with DAO class name it is being called from</p>
 * <p>ie</p>
 * <p>{@link QueryConstants#BATCH_PRODUCT_DAO_GET_ALL_BY_USER_ID_AND_STATUS} is being called from {@link BatchProductDao} so the variable name starts with: </p>
 * <i> BATCH_PRODUCT_DAO </i>
 */
public class QueryConstants {
    public static final String BATCH_PRODUCT_DAO_GET_ALL_BY_USER_ID_AND_STATUS = "select * from batch_product where id in (select batch_product_id from batch_product_right where user_id = ?1) and status = ?2";
    public static final String RAW_PRODUCT_DAO_GET_ALL_BY_STATE_ID = "select * from raw_product where id in(select raw_product_id from state_raw_product where state_id = 1?)";
    private QueryConstants() {
    }
}
