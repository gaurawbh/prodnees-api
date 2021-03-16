package com.prodnees.dao;

import com.prodnees.domain.State;
import com.prodnees.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDao extends JpaRepository<State, Integer> {
}
