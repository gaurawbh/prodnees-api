package com.prodnees.auth.controller;

import com.prodnees.auth.dao.BlockedJwtDao;
import com.prodnees.auth.dao.ForgotPasswordInfoDao;
import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.BlockedJwt;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.dto.SecPasswordDto;
import com.prodnees.auth.dto.TempPasswordDto;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.action.UserAction;
import com.prodnees.domain.user.UserAttributes;
import com.prodnees.model.user.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.prodnees.web.response.LocalResponse.configure;

/**
 * Should only use Entity and Dao classes from auth schema
 * <i>No {@link UserAttributes}</i>
 */

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class AuthUserController {

    private final BlockedJwtDao blockedJwtDao;
    private final UserAction userAction;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;

    public AuthUserController(BlockedJwtDao blockedJwtDao,
                              UserAction userAction,
                              TempPasswordInfoDao tempPasswordInfoDao,
                              ForgotPasswordInfoDao forgotPasswordInfoDao) {
        this.blockedJwtDao = blockedJwtDao;
        this.userAction = userAction;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
    }



    /**
     * After the password has been changed:
     * <p>add row to BlockedJwt</p>
     *
     * @param dto
     * @return
     */

    @PutMapping("/user/password")
    public ResponseEntity<?> updatePassword(@Validated @RequestBody SecPasswordDto dto) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        int userId = RequestContext.getUserId();
        User user = userAction.getById(userId);
        Assert.isTrue(passwordEncoder.matches(dto.getOldPassword(), user.getPassword()), "incorrect oldPassword, please try again");
        Assert.isTrue(dto.getNewPassword().equals(dto.getRepeatNewPassword()), "newPassword and repeatNewPassword do not match");
        Assert.isTrue(!dto.getOldPassword().equals(dto.getNewPassword()), "password not updated, your oldPassword and newPassword are same");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        UserModel userModel = userAction.save(user);
        String jwt = RequestContext.extractToken();
        blockedJwtDao.save(new BlockedJwt().setJwt(jwt).setUserId(user.getId()).setEmail(user.getEmail())); // add to BlockedJwt
        return configure(userModel);
    }

    /**
     * After the temp-password has been changed:
     * <p> remove row by email from ForgotPasswordInfo</p>
     * <p> remove row by email from TempPasswordInfo</p>
     * <p>add row to BlockedJwt</p>
     *
     * @param dto
     * @return
     */
    @PutMapping("/user/temp-password")
    public ResponseEntity<?> updateTemporaryPassword(@Validated @RequestBody TempPasswordDto dto) {
        Assert.isTrue(dto.getPassword().equals(dto.getRepeatPassword()), "password and repeatPassword do not match");
        int userId = RequestContext.getUserId();
        User user = userAction.getById(userId);
        boolean isValidRequest = tempPasswordInfoDao.existsByEmail(user.getEmail())
                || forgotPasswordInfoDao.existsByEmail(user.getEmail());
        Assert.isTrue(isValidRequest, "temporary password was not requested for you");// check that the temporary password was requested.
        boolean isTempPasswordJwt = RequestContext.isTempPassword();
        Assert.isTrue(isTempPasswordJwt, "temporary password must be used to change your temporary password");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserModel userModel = userAction.save(user);
        String jwt = RequestContext.extractToken();
        blockedJwtDao.save(new BlockedJwt().setJwt(jwt).setUserId(user.getId()).setEmail(user.getEmail())); // add to BlockedJwt
        forgotPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from ForgotPasswordInfo
        tempPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from TempPasswordInfo

        return configure(userModel);
    }




}

































