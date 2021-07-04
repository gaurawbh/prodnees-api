package com.prodnees.core.dao.doc;

import com.prodnees.core.domain.doc.NeesDoctype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeesDocTypeDao extends JpaRepository<NeesDoctype, Integer> {

    Optional<NeesDoctype> findByName(String name);
}
