package com.prodnees.shelf.dao;

import com.prodnees.shelf.domain.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGroupDao extends JpaRepository<ProductGroup, Integer> {
}
