package com.prodnees.service.impl;

import com.prodnees.dao.UserDetailsDao;
import com.prodnees.domain.UserDetails;
import com.prodnees.service.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsDao userDetailsDao;

    public UserDetailsServiceImpl(UserDetailsDao userDetailsDao) {
        this.userDetailsDao = userDetailsDao;
    }

    @Override
    public UserDetails save(UserDetails userDetails) {
        return userDetailsDao.save(userDetails);
    }

    @Override
    public UserDetails getByUserId(int userId) {
        return null;
    }

    @Override
    public UserDetails getByEmail(String email) {
        return null;
    }
}
