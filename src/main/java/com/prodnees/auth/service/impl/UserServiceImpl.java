package com.prodnees.auth.service.impl;

import com.prodnees.auth.dao.UserDao;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean existsById(int id) {
        return userDao.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
