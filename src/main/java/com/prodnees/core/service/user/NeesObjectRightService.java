package com.prodnees.core.service.user;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.domain.user.NeesObjectRight;

import java.util.List;

public interface NeesObjectRightService {

    NeesObjectRight save(NeesObjectRight objectRight);

    List<NeesObjectRight> getAllByUserId(int userId);

    NeesObjectRight getByUserIdAndNeesObject(int userId, NeesObject object);

    boolean hasFullObjectRight(int userId, NeesObject object);

    boolean hasUpdateObjectRight(int userId, NeesObject object);

    boolean hasViewObjectRight(int userId, NeesObject object);

}
