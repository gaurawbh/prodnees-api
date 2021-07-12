package com.prodnees.core.service.user.impl;

import com.prodnees.core.dao.user.UserAttributesDao;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.model.user.NeesUserDetails;
import com.prodnees.core.service.user.UserAttributesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAttributesServiceImpl implements UserAttributesService {
    private final UserAttributesDao userAttributesDao;

    public UserAttributesServiceImpl(UserAttributesDao userAttributesDao) {
        this.userAttributesDao = userAttributesDao;
    }

    @Override
    public UserAttributes save(UserAttributes userAttributes) {
        return userAttributesDao.save(userAttributes);
    }

    @Override
    public UserAttributes getByUserId(int userId) {
        return userAttributesDao.getByUserId(userId);
    }

    @Override
    public NeesUserDetails getNeesUserDetails(int userId) {
        return null;
    }

    @Override
    public UserAttributes getByEmail(String email) {
        return userAttributesDao.getByEmail(email);
    }

    @Override
    public void deleteByUserId(int id) {
        userAttributesDao.deleteById(id);
    }

    @Override
    public List<UserAttributes> findAll() {
        return userAttributesDao.findAll();
    }
}
