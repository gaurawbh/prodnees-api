package com.prodnees.auth.dao;

import com.prodnees.auth.domain.TempPasswordInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPasswordInfoDao extends JpaRepository<TempPasswordInfo, Integer> {
    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
