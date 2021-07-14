package com.prodnees.core.service.user.impl;

import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.service.UserService;
import com.prodnees.core.dao.user.NeesObjectRightDao;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.domain.user.NeesObjectRight;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.util.LocalAssert;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeesObjectRightServiceImpl implements NeesObjectRightService {
    private final NeesObjectRightDao neesObjectRightDao;
    private final UserService userService;

    public NeesObjectRightServiceImpl(NeesObjectRightDao neesObjectRightDao,
                                      UserService userService) {
        this.neesObjectRightDao = neesObjectRightDao;
        this.userService = userService;
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
        return neesObjectRightDao.findByUserIdAndNeesObject(userId, object)
                .orElseGet(() -> {
                    ObjectRight objectRight;
                    if (RequestContext.getUserRole().equals(ApplicationRole.appOwner.name()) || RequestContext.getUserRole().equals(ApplicationRole.sysAdmin.name())) {
                        objectRight = ObjectRight.full;
                    } else {
                        objectRight = ObjectRight.noAccess;
                    }
                    NeesObjectRight right = new NeesObjectRight()
                            .setUserId(userId)
                            .setNeesObject(object)
                            .setObjectRight(objectRight);
                    return neesObjectRightDao.save(right);
                });
    }

    @Override
    public boolean hasFullObjectRight(int userId, NeesObject object) {
        NeesObjectRight objectRight = getByUserIdAndNeesObject(userId, object);
        return objectRight.getObjectRight().equals(ObjectRight.full);
    }

    @Override
    public boolean hasUpdateObjectRight(int userId, NeesObject object) {
        NeesObjectRight objectRight = getByUserIdAndNeesObject(userId, object);
        return objectRight.getObjectRight().equals(ObjectRight.full)
                || objectRight.getObjectRight().equals(ObjectRight.update);
    }

    @Override
    public boolean hasViewObjectRight(int userId, NeesObject object) {
        NeesObjectRight objectRight = getByUserIdAndNeesObject(userId, object);
        return !objectRight.getObjectRight().equals(ObjectRight.noAccess);
    }

    @Override
    public NeesObjectRight update(NeesObjectRight objectRight) {
        User user = userService.getById(objectRight.getUserId());
        LocalAssert.isTrue(user.getRole().equals(ApplicationRole.admin), "Cannot modify Object Right of an appOwner or sysAdmin");
        return neesObjectRightDao.save(objectRight);
    }
}
