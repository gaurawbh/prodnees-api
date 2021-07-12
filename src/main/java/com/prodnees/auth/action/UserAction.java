package com.prodnees.auth.action;

import com.prodnees.auth.domain.User;

public interface UserAction {
    boolean existsById(int id);

    boolean existsByEmail(String email);

    User save(User user);

    User getById(int id);

    User getByEmail(String email);

}
