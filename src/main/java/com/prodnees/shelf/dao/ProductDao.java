package com.prodnees.shelf.dao;

import com.prodnees.shelf.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {

    Product getById(int id);

    Product getByName(String name);

    void deleteById(int id);

    List<Product> getAllByNameLike(String wildCard);
}
