package com.prodnees.core.action.impl;

import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.auth.domain.TempPasswordInfo;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.service.UserService;
import com.prodnees.auth.util.OtpUtil;
import com.prodnees.auth.util.TemporaryPasswordHelper;
import com.prodnees.core.action.SysAdminAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.domain.user.NeesObjectRight;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.user.ApplicationUserDto;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.util.LocalAssert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SysAdminActionImpl implements SysAdminAction {
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final LocalEmailService localEmailService;
    private final NeesObjectRightService objectRightService;
    public SysAdminActionImpl(UserService userService,
                              UserAttributesService userAttributesService,
                              TempPasswordInfoDao tempPasswordInfoDao,
                              LocalEmailService localEmailService,
                              NeesObjectRightService objectRightService) {
        this.userService = userService;
        this.userAttributesService = userAttributesService;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.localEmailService = localEmailService;
        this.objectRightService = objectRightService;
    }

    @Override
    public User disableUser(int id) {
        User user = userService.getById(id)
                .setEnabled(false);
        return userService.save(user);
    }



    @Override
    @Transactional
    public UserAttributes addApplicationUser(ApplicationUserDto dto) {
        LocalAssert.isFalse(userService.existsByEmail(dto.getEmail()), APIErrors.EMAIL_TAKEN);
        int ownerId = RequestContext.getUserId();
        User loggedInUser = userService.getById(ownerId);

        if (loggedInUser.getRole().equals(ApplicationRole.sysAdmin)) {
            LocalAssert.isFalse(dto.getRole().equals(ApplicationRole.appOwner), "Sys admin cannot add an appOwner");

        }
        String tempPassword = getTempPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(tempPassword);

        User applicationUser = new User();
        applicationUser.setEmail(dto.getEmail())
                .setPassword(password)
                .setCompanyId(loggedInUser.getCompanyId())
                .setSchemaInstance(loggedInUser.getSchemaInstance())
                .setEnabled(true)
                .setRole(dto.getRole())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setEnabled(true);
        applicationUser = userService.save(applicationUser);

        TempPasswordInfo tempPasswordInfo = new TempPasswordInfo()
                .setEmail(dto.getEmail())
                .setCreatedDateTime(LocalDateTime.now());
        TempPasswordInfo tempPasswordInfo1 = tempPasswordInfoDao.save(tempPasswordInfo);
        new TemporaryPasswordHelper(localEmailService).emailTemporaryPassword(applicationUser.getEmail(), tempPassword, loggedInUser.getCompanyId());

        UserAttributes userAttributes = new UserAttributes();
        userAttributes.setUserId(applicationUser.getId())
                .setEmail(applicationUser.getEmail())
                .setRole(applicationUser.getRole())
                .setFirstName(applicationUser.getFirstName())
                .setLastName(applicationUser.getLastName());
        userAttributes = userAttributesService.save(userAttributes);
        ObjectRight objectRight = applicationUser.getRole().equals(ApplicationRole.admin)
                ? ObjectRight.noAccess
                : ObjectRight.full;
        issueObjectRights(applicationUser.getId(), objectRight);
        return userAttributes;
    }

    private void issueObjectRights(int userId, ObjectRight objectRight) {
        NeesObject[] neesObjects = NeesObject.values();
        for (NeesObject neesObject : neesObjects) {
            NeesObjectRight neesObjectRight = new NeesObjectRight();
            neesObjectRight.setUserId(userId)
                    .setNeesObject(neesObject)
                    .setObjectRight(objectRight);
            objectRightService.save(neesObjectRight);
        }
    }



    private String getTempPassword() {
        return OtpUtil.generateRandomOtp(6);

    }
}
