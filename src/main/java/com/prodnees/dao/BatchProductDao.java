package com.prodnees.dao;

import com.prodnees.domain.BatchProduct;
import com.prodnees.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchProductDao extends JpaRepository<BatchProduct, Integer> {
}
