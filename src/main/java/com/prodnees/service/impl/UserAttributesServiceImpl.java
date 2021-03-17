package com.prodnees.service.impl;

import com.prodnees.dao.UserAttributesDao;
import com.prodnees.domain.UserAttributes;
import com.prodnees.service.UserAttributesService;
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
        return null;
    }

    @Override
    public UserAttributes getByEmail(String email) {
        return null;
    }
}
