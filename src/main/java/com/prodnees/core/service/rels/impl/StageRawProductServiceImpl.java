package com.prodnees.core.service.rels.impl;

import com.prodnees.core.dao.rels.StageRawProductDao;
import com.prodnees.core.domain.rels.StageRawProduct;
import com.prodnees.core.service.rels.StageRawProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StageRawProductServiceImpl implements StageRawProductService {
    private final StageRawProductDao stageRawProductDao;

    public StageRawProductServiceImpl(StageRawProductDao stageRawProductDao) {
        this.stageRawProductDao = stageRawProductDao;
    }

    @Override
    public StageRawProduct save(StageRawProduct stageRawProduct) {
        return stageRawProductDao.save(stageRawProduct);
    }

    @Override
    public Optional<StageRawProduct> getByStageIdAndRawProductId(int stageId, int rawProductId) {
        return stageRawProductDao.findByStageIdAndRawProductId(stageId, rawProductId);
    }

    @Override
    public List<StageRawProduct> getAllByStageId(int stageId) {
        return stageRawProductDao.getAllByStageId(stageId);
    }

    @Override
    public List<StageRawProduct> getAllByRawProductId(int rawProductId) {
        return stageRawProductDao.getAllByRawProductId(rawProductId);
    }
}
