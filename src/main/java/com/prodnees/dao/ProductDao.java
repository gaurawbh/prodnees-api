package com.prodnees.dao;

import com.prodnees.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {

    Product getById(int id);

    Product getByName(String name);
}
