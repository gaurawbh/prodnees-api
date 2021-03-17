package com.prodnees.dao;

import com.prodnees.domain.BatchProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchProductDao extends JpaRepository<BatchProduct, Integer> {
}
