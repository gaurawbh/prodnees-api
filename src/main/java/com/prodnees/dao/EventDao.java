package com.prodnees.dao;

import com.prodnees.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDao extends JpaRepository<Event, Integer> {
}
