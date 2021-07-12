package com.prodnees.core.action.impl;

import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.service.UserService;
import com.prodnees.core.action.ApplicationOwnerAction;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.util.LocalAssert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ApplicationOwnerActionImpl implements ApplicationOwnerAction {
    private final UserAttributesService userAttributesService;
    private final UserService userService;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final LocalEmailService localEmailService;
    private final NeesObjectRightService objectRightService;

    public ApplicationOwnerActionImpl(UserAttributesService userAttributesService,
                                      UserService userService,
                                      TempPasswordInfoDao tempPasswordInfoDao,
                                      LocalEmailService localEmailService,
                                      NeesObjectRightService objectRightService) {
        this.userAttributesService = userAttributesService;
        this.userService = userService;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.localEmailService = localEmailService;
        this.objectRightService = objectRightService;
    }

    @Override
    @Transactional
    public void deleteApplicationUser(int id) {
        userService.deleteById(id);
        userAttributesService.deleteByUserId(id);

    }

    @Override
    public UserAttributes updateApplicationRole(int userId, ApplicationRole role) {
        LocalAssert.isTrue(RequestContext.getUserRole().equals(ApplicationRole.appOwner), "Insufficient right to execute this request");

        User user = userService.getById(userId);
        user.setRole(role);
        userService.save(user);

        UserAttributes userAttributes = userAttributesService.getByUserId(userId);
        userAttributes.setRole(role);

        return userAttributesService.save(userAttributes);
    }


}
