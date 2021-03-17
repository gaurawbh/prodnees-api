package com.prodnees.dao;

import com.prodnees.domain.UserAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAttributesDao extends JpaRepository<UserAttributes, Integer> {
}
