/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.dao;


import com.prodnees.domain.JwtTail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTailDao extends JpaRepository<JwtTail, Integer> {

    boolean existsByUserId(int userId);

    boolean existsByTail(String tail);

    boolean existsByUserIdAndTail(int userId, String tail);

    boolean existsByEmailAndTail(String email, String tail);

    JwtTail getByEmail(String email);

    JwtTail getByUserId(int userId);
}
