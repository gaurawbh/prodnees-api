package com.prodnees.service.impl;

import com.prodnees.dao.UserDao;
import com.prodnees.domain.User;
import com.prodnees.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * todo encrypt user password before saving the user
     * @param user
     * @return
     */
    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }
}
