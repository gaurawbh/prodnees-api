package com.prodnees.service.user;

import com.prodnees.domain.user.UserAttributes;

public interface UserAttributesService {

    UserAttributes save(UserAttributes user);

    UserAttributes getByUserId(int userId);

    UserAttributes getByEmail(String email);

}
