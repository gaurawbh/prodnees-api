package com.prodnees.service;

import com.prodnees.domain.User;

public interface UserService {

    User save(User user);

    User getById(int id);

    User getByEmail(String email);

}
