package com.prodnees.core.service.user;

import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.model.user.NeesUserDetails;

import java.util.List;

public interface UserAttributesService {

    UserAttributes save(UserAttributes user);

    UserAttributes getByUserId(int userId);

    NeesUserDetails getNeesUserDetails(int userId);

    UserAttributes getByEmail(String email);

    void deleteByUserId(int id);

    List<UserAttributes> findAll();
}
