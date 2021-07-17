package com.prodnees.shelf.dao;

import com.prodnees.shelf.domain.GroupProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupProductDao extends JpaRepository<GroupProduct, Integer> {
}
