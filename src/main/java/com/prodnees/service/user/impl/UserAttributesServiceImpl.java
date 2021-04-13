package com.prodnees.service.user.impl;

import com.prodnees.dao.user.UserAttributesDao;
import com.prodnees.domain.user.UserAttributes;
import com.prodnees.service.user.UserAttributesService;
import org.springframework.stereotype.Service;

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
    public UserAttributes getByEmail(String email) {
        return userAttributesDao.getByEmail(email);
    }
}
