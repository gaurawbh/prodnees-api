package com.prodnees.auth.dao;

import com.prodnees.auth.domain.BlockedJwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedJwtDao extends JpaRepository<BlockedJwt, Integer> {

    boolean existsByJwt(String jwt);

}
