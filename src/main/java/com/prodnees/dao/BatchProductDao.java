package com.prodnees.dao;

import com.prodnees.domain.BatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchProductDao extends JpaRepository<BatchProduct, Integer> {

    BatchProduct getById(int id);

    List<BatchProduct> getAllByProductId(int productId);


}
