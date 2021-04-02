package com.prodnees.service;

import com.prodnees.domain.user.User;

public interface UserService {

    boolean existsById(int id);

    boolean existsByEmail(String email);
    User save(User user);

    User getById(int id);

    User getByEmail(String email);

}
