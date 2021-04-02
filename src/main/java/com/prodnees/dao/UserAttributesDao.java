package com.prodnees.dao;

import com.prodnees.domain.user.UserAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAttributesDao extends JpaRepository<UserAttributes, Integer> {
    UserAttributes getByUserId(int userId);

    UserAttributes getByEmail(String email);
}
