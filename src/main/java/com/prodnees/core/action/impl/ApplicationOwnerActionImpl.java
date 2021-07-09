package com.prodnees.core.action.impl;

import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.TempPasswordInfo;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.service.UserService;
import com.prodnees.auth.util.OtpUtil;
import com.prodnees.auth.util.TemporaryPasswordHelper;
import com.prodnees.core.action.ApplicationOwnerAction;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.user.ApplicationUserDto;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.user.UserAttributesService;
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
        int ownerId = RequestContext.getUserId();
        User loggedInUser = userService.getById(ownerId);
//        if (!loggedInUser.getApplicationRight().equals(ApplicationRight.owner)) {
//            throw new NeesBadRequestException("Only application owners can add a new Application user");
//        }
        String tempPassword = getTempPassword();

        User applicationUser = new User();
        applicationUser.setEmail(dto.getEmail())
                .setPassword(tempPassword)
                .setCompanyId(loggedInUser.getCompanyId())
                .setSchemaInstance(loggedInUser.getSchemaInstance())
                .setEnabled(true)
                .setRole(dto.getApplicationRole())
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

    private String getTempPassword() {
        return OtpUtil.generateRandomOtp(6);

    }

}
