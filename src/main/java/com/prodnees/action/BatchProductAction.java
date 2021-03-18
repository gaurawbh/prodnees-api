package com.prodnees.action;

import com.prodnees.domain.BatchProduct;
import com.prodnees.model.BatchProductModel;
import java.util.List;

public interface BatchProductAction {

    BatchProductModel save(BatchProduct batchProduct);

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);
}
