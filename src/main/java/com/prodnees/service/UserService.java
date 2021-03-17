package com.prodnees.service;

import com.prodnees.domain.User;

public interface UserService {

    boolean existsById(int id);

    boolean existsByEmail(String email);
    User save(User user);

    User getById(int id);

    User getByEmail(String email);

}
