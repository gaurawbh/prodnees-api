package com.prodnees.core.service.user.impl;

import com.prodnees.core.dao.user.NeesObjectRightDao;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.domain.user.NeesObjectRight;
import com.prodnees.core.service.user.NeesObjectRightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeesObjectRightServiceImpl implements NeesObjectRightService {
    private final NeesObjectRightDao neesObjectRightDao;

    public NeesObjectRightServiceImpl(NeesObjectRightDao neesObjectRightDao) {
        this.neesObjectRightDao = neesObjectRightDao;
    }

    @Override
    public NeesObjectRight save(NeesObjectRight objectRight) {
        return neesObjectRightDao.save(objectRight);
    }

    @Override
    public List<NeesObjectRight> getAllByUserId(int userId) {
        return neesObjectRightDao.getAllByUserId(userId);
    }

    @Override
    public NeesObjectRight getByUserIdAndNeesObject(int userId, NeesObject object) {
        return neesObjectRightDao.getByUserIdAndNeesObject(userId, object);
    }

    @Override
    public boolean hasFullObjectRight(int userId, NeesObject object) {
        NeesObjectRight objectRight = neesObjectRightDao.getByUserIdAndNeesObject(userId, object);
        return objectRight.getObjectRight().equals(ObjectRight.full);
    }

    @Override
    public boolean hasUpdateObjectRight(int userId, NeesObject object) {
        NeesObjectRight objectRight = neesObjectRightDao.getByUserIdAndNeesObject(userId, object);
        return objectRight.getObjectRight().equals(ObjectRight.full)
                || objectRight.getObjectRight().equals(ObjectRight.update);
    }

    @Override
    public boolean hasViewObjectRight(int userId, NeesObject object) {
        NeesObjectRight objectRight = neesObjectRightDao.getByUserIdAndNeesObject(userId, object);
        return !objectRight.getObjectRight().equals(ObjectRight.noAccess);
    }
}
