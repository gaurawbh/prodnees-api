package com.prodnees.dao;

import com.prodnees.domain.ForgotPasswordInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForgotPasswordInfoDao extends JpaRepository<ForgotPasswordInfo, Integer> {

    boolean existsByEmail(String email);

    ForgotPasswordInfo getByEmail(String email);

    Optional<ForgotPasswordInfo> findByEmail(String email);

    void deleteByEmail(String email);

}
