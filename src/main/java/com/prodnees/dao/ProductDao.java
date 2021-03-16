package com.prodnees.dao;

import com.prodnees.domain.Product;
import com.prodnees.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
