package com.prodnees.dao;

import com.prodnees.domain.Document;
import com.prodnees.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentDao extends JpaRepository<Document, Integer> {
}
