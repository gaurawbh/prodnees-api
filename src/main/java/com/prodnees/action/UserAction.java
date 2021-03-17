package com.prodnees.action;

import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.model.UserModel;

public interface UserAction {
    boolean existsById(int id);

    boolean existsByEmail(String email);

    UserModel save(UserRegistrationDto dto);

    UserModel getById(int id);

    UserModel getByEmail(String email);
}
