package com.prodnees.service;

import com.prodnees.domain.UserAttributes;

public interface UserAttributesService {

    UserAttributes save(UserAttributes user);

    UserAttributes getByUserId(int userId);

    UserAttributes getByEmail(String email);

}
