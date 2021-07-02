/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.annotations.VisibleForTesting;
import com.prodnees.auth.dao.CompanyDao;
import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.dao.UserDao;
import com.prodnees.auth.domain.ApplicationRight;
import com.prodnees.auth.domain.Company;
import com.prodnees.auth.domain.TempPasswordInfo;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.domain.UserRole;
import com.prodnees.auth.service.SignupService;
import com.prodnees.auth.service.TenantService;
import com.prodnees.auth.util.OtpUtil;
import com.prodnees.auth.util.TemporaryPasswordHelper;
import com.prodnees.auth.util.TenantUtil;
import com.prodnees.core.dto.user.SignupDto;
import com.prodnees.core.model.user.UserModel;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.web.exception.NeesBadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.prodnees.core.config.constants.APIErrors.EMAIL_TAKEN;


@Service
public class SignupServiceImpl implements SignupService {

    private final UserDao userDao;
    private final CompanyDao companyDao;
    private final LocalEmailService emailService;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final TenantService tenantService;


    public SignupServiceImpl(UserDao userDao,
                             CompanyDao companyDao,
                             LocalEmailService emailService,
                             TempPasswordInfoDao tempPasswordInfoDao,
                             TenantService tenantService) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        this.emailService = emailService;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.tenantService = tenantService;
    }

    /**
     * Perform in sequence:
     * <p>save {@link Company}</p>
     * <p>save {@link com.prodnees.auth.domain.User}</p>
     * <p>save {@link com.prodnees.core.domain.user.UserAttributes}</p>
     * <p>save {@link TempPasswordInfo}</p>
     * <p>email {@link TempPasswordInfo} to the new user</p>
     *
     * @param dto
     * @return
     */
    @Override
    public UserModel signup(SignupDto dto) throws JsonProcessingException {
        Company company = registerCompany(dto);
        String tempPassword = getTempPassword();
        User user = registerUser(dto, tempPassword, company.getId());

        tenantService.createNewSchema(user);

        TempPasswordInfo tempPasswordInfo = new TempPasswordInfo()
                .setEmail(user.getEmail())
                .setCreatedDateTime(LocalDateTime.now());
        TempPasswordInfo tempPasswordInfo1 = tempPasswordInfoDao.save(tempPasswordInfo);
        new TemporaryPasswordHelper(emailService).emailTemporaryPassword(user.getEmail(), tempPassword, company.getId());

        return new UserModel()
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setCompanyId(company.getId())
                .setId(user.getId())
                .setRole(user.getRole());
    }

    @VisibleForTesting
    Company registerCompany(SignupDto dto) {
        if (companyDao.existsByName(dto.getCompanyName())) {
            throw new NeesBadRequestException("COMPANY_ALREADY_REGISTERED");
        }
        Company company = new Company()
                .setName(dto.getCompanyName())
                .setEmail(dto.getEmail())
                .setPhoneNumber(dto.getPhoneNumber())
                .setZoneId("Z")//UTC
                .setActive(true);
        return companyDao.save(company);
    }

    @VisibleForTesting
    User registerUser(SignupDto dto, String tempPassword, int companyId) {
        LocalAssert.isFalse(userDao.existsByEmail(dto.getEmail()), EMAIL_TAKEN);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(tempPassword);

        String schema = TenantUtil.newSchema(companyId);
        User user = new User()
                .setEmail(dto.getEmail())
                .setRole(UserRole.owner)
                .setPassword(password)
                .setEnabled(true)
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setCompanyId(companyId)
                .setApplicationRight(ApplicationRight.owner)
                .setSchemaInstance(schema);
        Company company = companyDao.getById(companyId);
        companyDao.save(company.setSchemaInstance(schema));// save schemaInstance to Company
        return userDao.save(user);
    }

    @VisibleForTesting
    String getTempPassword() {
        return OtpUtil.generateRandomOtp(6);

    }


}
