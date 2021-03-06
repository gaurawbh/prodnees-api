package com.prodnees.auth.service;

import com.prodnees.auth.domain.User;

public interface UserService {

    boolean existsById(int id);

    boolean existsByEmail(String email);

    User save(User user);

    User getById(int id);

    User getByEmail(String email);

    void deleteById(int id);

}
