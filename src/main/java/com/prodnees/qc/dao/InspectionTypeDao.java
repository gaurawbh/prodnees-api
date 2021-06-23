package com.prodnees.qc.dao;

import com.prodnees.qc.domain.InspectionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InspectionTypeDao extends JpaRepository<InspectionType, Integer> {
}
