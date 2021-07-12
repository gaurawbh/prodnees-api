/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.auth.dto.SignupDto;
import com.prodnees.core.model.user.NeesUserDetails;

public interface SignupService {

    NeesUserDetails signup(SignupDto dto) throws JsonProcessingException;

}
