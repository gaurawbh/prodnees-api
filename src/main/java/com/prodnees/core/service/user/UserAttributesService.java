package com.prodnees.core.service.user;

import com.prodnees.core.domain.user.UserAttributes;

public interface UserAttributesService {

    UserAttributes save(UserAttributes user);

    UserAttributes getByUserId(int userId);

    UserAttributes getByEmail(String email);

}
