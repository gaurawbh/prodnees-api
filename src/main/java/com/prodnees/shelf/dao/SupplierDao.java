/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.shelf.dao;


import com.prodnees.shelf.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierDao extends JpaRepository<Supplier, Integer> {

    boolean existsByName(String name);

    List<Supplier> findByNameContaining(String nameWildCard);

}
