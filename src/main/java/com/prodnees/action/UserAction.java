package com.prodnees.action;

import com.prodnees.domain.User;
import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.model.UserModel;

public interface UserAction {
    boolean existsById(int id);

    boolean existsByEmail(String email);

    UserModel save(UserRegistrationDto dto);

    UserModel save(User user);

    UserModel getModelById(int id);

    UserModel getModelByEmail(String email);


    User getById(int id);

    User getByEmail(String email);


}
