package com.prodnees.dao.user;

import com.prodnees.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User getById(int id);

    boolean existsByEmail(String email);

    @Query(nativeQuery = true, value = "create schema 'ola'")
    void createSchema();
}
