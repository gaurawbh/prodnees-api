package com.prodnees.core.dao.user;

import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.domain.user.NeesObjectRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NeesObjectRightDao extends JpaRepository<NeesObjectRight, Integer> {
    List<NeesObjectRight> getAllByUserId(int userId);

    NeesObjectRight getByUserIdAndNeesObject(int userId, NeesObject object);

}
