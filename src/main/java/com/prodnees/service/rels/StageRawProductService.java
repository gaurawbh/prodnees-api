package com.prodnees.service.rels;

import com.prodnees.domain.rels.StageRawProduct;

import java.util.List;
import java.util.Optional;

public interface StageRawProductService {

    StageRawProduct save(StageRawProduct stageRawProduct);

    Optional<StageRawProduct> getByStageIdAndRawProductId(int stageId, int rawProductId);

    List<StageRawProduct> getAllByStageId(int stageId);

    List<StageRawProduct> getAllByRawProductId(int rawProductId);

}
