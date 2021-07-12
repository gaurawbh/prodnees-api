package com.prodnees.auth.action.impl;

import com.prodnees.auth.action.UserAction;
import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.service.UserService;
import com.prodnees.core.model.user.NeesUserDetails;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.user.UserAttributesService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class UserActionImpl implements UserAction {
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final LocalEmailService localEmailService;
    private final TempPasswordInfoDao tempPasswordInfoDao;

    public UserActionImpl(UserService userService,
                          UserAttributesService userAttributesService,
                          LocalEmailService localEmailService,
                          TempPasswordInfoDao tempPasswordInfoDao) {
        this.userService = userService;
        this.userAttributesService = userAttributesService;
        this.localEmailService = localEmailService;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
    }

    @Override
    public boolean existsById(int id) {
        return userService.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userService.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userService.save(user);
    }

    @Override
    public User getById(int id) {
        return userService.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userService.getByEmail(email);
    }

    NeesUserDetails mapToUserModel(User user) {
        NeesUserDetails model = new NeesUserDetails();
        return model.setId(user.getId())
                .setEmail(user.getEmail())
                .setRole(user.getRole())
                .setEnabled(user.isEnabled())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
    }

}
