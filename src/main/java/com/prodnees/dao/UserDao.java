package com.prodnees.dao;

import com.prodnees.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User getById(int id);

    boolean existsByEmail(String email);
}
