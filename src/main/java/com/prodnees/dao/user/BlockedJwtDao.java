package com.prodnees.dao.user;

import com.prodnees.domain.user.BlockedJwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedJwtDao extends JpaRepository<BlockedJwt, Integer> {

    boolean existsByJwt(String jwt);

}
