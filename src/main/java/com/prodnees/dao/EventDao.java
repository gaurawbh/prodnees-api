package com.prodnees.dao;

import com.prodnees.domain.Event;
import com.prodnees.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Integer> {
}
