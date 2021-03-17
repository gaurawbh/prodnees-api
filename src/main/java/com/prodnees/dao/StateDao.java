package com.prodnees.dao;

import com.prodnees.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateDao extends JpaRepository<State, Integer> {
}
