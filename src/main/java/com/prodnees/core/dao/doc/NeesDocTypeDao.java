package com.prodnees.core.dao.doc;

import com.prodnees.core.domain.doc.NeesDocType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeesDocTypeDao extends JpaRepository<NeesDocType, Integer> {

    Optional<NeesDocType> findByName(String name);
}
