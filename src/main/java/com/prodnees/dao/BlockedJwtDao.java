package com.prodnees.dao;

import com.prodnees.domain.BlockedJwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedJwtDao extends JpaRepository<BlockedJwt, Integer> {

    boolean existsByJwt(String jwt);

}
