/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.dao;

import com.prodnees.auth.domain.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyDao extends JpaRepository<Company, Integer> {

    Company getById(int id);

    boolean existsByName(String companyName);

    @NonNull
    Page<Company> findAll(@NonNull Pageable pageable);

    Page<Company> getAllByActive(boolean active, Pageable pageable);

//    @Query(value = "select schemaInstance from Company")
//    List<String> getAllSchemas();

}
