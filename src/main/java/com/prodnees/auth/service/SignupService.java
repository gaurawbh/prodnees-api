/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.service;


import com.prodnees.dto.user.SignupDto;
import com.prodnees.model.user.UserModel;

public interface SignupService {

    UserModel signup(SignupDto dto);

}
