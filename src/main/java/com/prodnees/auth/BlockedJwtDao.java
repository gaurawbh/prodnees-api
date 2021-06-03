package com.prodnees.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedJwtDao extends JpaRepository<BlockedJwt, Integer> {

    boolean existsByJwt(String jwt);

}
