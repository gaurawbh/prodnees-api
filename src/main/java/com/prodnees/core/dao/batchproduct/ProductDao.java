package com.prodnees.core.dao.batchproduct;

import com.prodnees.core.domain.batch.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {

    Product getById(int id);

    Product getByName(String name);

    void deleteById(int id);
}
