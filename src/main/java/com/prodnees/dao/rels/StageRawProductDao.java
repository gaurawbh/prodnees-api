package com.prodnees.dao.rels;

import com.prodnees.domain.rels.StageRawProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StageRawProductDao extends JpaRepository<StageRawProduct, Integer> {
    Optional<StageRawProduct> findByStageIdAndRawProductId(int stageId, int rawProductId);

    List<StageRawProduct> getAllByStageId(int stageId);

    List<StageRawProduct> getAllByRawProductId(int rawProductId);

}
