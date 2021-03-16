package com.prodnees.dao;

import com.prodnees.domain.User;
import com.prodnees.domain.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsDao extends JpaRepository<UserDetails, Integer> {
}
