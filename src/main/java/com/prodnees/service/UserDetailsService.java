package com.prodnees.service;

import com.prodnees.domain.User;
import com.prodnees.domain.UserDetails;

public interface UserDetailsService {

    UserDetails save(UserDetails user);

    UserDetails getByUserId(int userId);

    UserDetails getByEmail(String email);

}
