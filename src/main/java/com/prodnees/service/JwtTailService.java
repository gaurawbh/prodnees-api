/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.service;

import com.prodnees.domain.JwtTail;
import com.prodnees.domain.User;

public interface JwtTailService {

    boolean existsByUserId(int userId);

    JwtTail getByEmail(String email);

    JwtTail getByUserId(int userId);

    String generateAndUpdateNextTail(User user);

    boolean isValidTail(String email, String jwtTail);

}
