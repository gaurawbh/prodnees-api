package com.prodnees.core.action.impl;

import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.auth.domain.TempPasswordInfo;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.service.UserService;
import com.prodnees.auth.util.OtpUtil;
import com.prodnees.auth.util.TemporaryPasswordHelper;
import com.prodnees.core.action.ApplicationOwnerAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.user.ApplicationUserDto;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.util.LocalAssert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationOwnerActionImpl implements ApplicationOwnerAction {
    private final UserAttributesService userAttributesService;
    private final UserService userService;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final LocalEmailService localEmailService;

    public ApplicationOwnerActionImpl(UserAttributesService userAttributesService,
                                      UserService userService,
                                      TempPasswordInfoDao tempPasswordInfoDao,
                                      LocalEmailService localEmailService) {
        this.userAttributesService = userAttributesService;
        this.userService = userService;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.localEmailService = localEmailService;
    }


    @Override
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
        return userAttributesService.save(userAttributes);
    }

    @Override
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

    private String getTempPassword() {
        return OtpUtil.generateRandomOtp(6);

    }

}
