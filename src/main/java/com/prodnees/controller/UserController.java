package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.dao.BlockedJwtDao;
import com.prodnees.dao.ForgotPasswordInfoDao;
import com.prodnees.dao.TempPasswordInfoDao;
import com.prodnees.domain.BlockedJwt;
import com.prodnees.domain.user.User;
import com.prodnees.domain.user.UserAttributes;
import com.prodnees.dto.SecPasswordDto;
import com.prodnees.dto.TempPasswordDto;
import com.prodnees.dto.UserAttributesDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.UserModel;
import com.prodnees.service.UserAttributesService;
import com.prodnees.util.ValidatorUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import static com.prodnees.web.response.LocalResponse.configure;


@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class UserController {

    private final BlockedJwtDao blockedJwtDao;
    private final UserAction userAction;
    private final RequestValidator requestValidator;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;
    private final UserAttributesService userAttributesService;

    public UserController(BlockedJwtDao blockedJwtDao,
                          UserAction userAction,
                          RequestValidator requestValidator,
                          TempPasswordInfoDao tempPasswordInfoDao,
                          ForgotPasswordInfoDao forgotPasswordInfoDao,
                          UserAttributesService userAttributesService) {
        this.blockedJwtDao = blockedJwtDao;
        this.userAction = userAction;
        this.requestValidator = requestValidator;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
        this.userAttributesService = userAttributesService;
    }

    /**
     * @param servletRequest
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return configure(userAction.getModelById(userId));
    }

    /**
     * After the password has been changed:
     * <p>add row to BlockedJwt</p>
     *
     * @param dto
     * @param servletRequest
     * @return
     */

    @PutMapping("/user/password")
    public ResponseEntity<?> updatePassword(@Validated @RequestBody SecPasswordDto dto,
                                            HttpServletRequest servletRequest) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        int userId = requestValidator.extractUserId(servletRequest);
        User user = userAction.getById(userId);
        Assert.isTrue(passwordEncoder.matches(dto.getOldPassword(), user.getPassword()), "incorrect oldPassword, please try again");
        Assert.isTrue(dto.getNewPassword().equals(dto.getRepeatNewPassword()), "newPassword and repeatNewPassword do not match");
        Assert.isTrue(!dto.getOldPassword().equals(dto.getNewPassword()), "password not updated, your oldPassword and newPassword are same");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        UserModel userModel = userAction.save(user);
        String jwt = requestValidator.extractToken(servletRequest);
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
     * @param servletRequest
     * @return
     */
    @PutMapping("/user/temp-password")
    public ResponseEntity<?> updateTemporaryPassword(@Validated @RequestBody TempPasswordDto dto,
                                                     HttpServletRequest servletRequest) {
        Assert.isTrue(dto.getPassword().equals(dto.getRepeatPassword()), "password and repeatPassword do not match");
        int userId = requestValidator.extractUserId(servletRequest);
        User user = userAction.getById(userId);
        boolean isValidRequest = tempPasswordInfoDao.existsByEmail(user.getEmail())
                || forgotPasswordInfoDao.existsByEmail(user.getEmail());
        Assert.isTrue(isValidRequest, "temporary password was not requested for you");// check that the temporary password was requested.
        boolean isTempPasswordJwt = requestValidator.hasUsedTempPassword(servletRequest);
        Assert.isTrue(isTempPasswordJwt, "temporary password must be used to change your temporary password");
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserModel userModel = userAction.save(user);
        String jwt = requestValidator.extractToken(servletRequest);
        blockedJwtDao.save(new BlockedJwt().setJwt(jwt).setUserId(user.getId()).setEmail(user.getEmail())); // add to BlockedJwt
        forgotPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from ForgotPasswordInfo
        tempPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from TempPasswordInfo

        return configure(userModel);
    }

    @PutMapping("/user")
    public ResponseEntity<?> update(@Validated @RequestBody UserAttributesDto dto,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        UserAttributes userAttributes = userAttributesService.getByUserId(userId);
        userAttributes.setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setAddress(ValidatorUtil.ifValidStringOrElse(dto.getAddress(), userAttributes.getAddress()))
                .setPhoneNumber(ValidatorUtil.ifValidStringOrElse(dto.getPhoneNumber(), userAttributes.getPhoneNumber()));

        return configure(userAttributesService.save(userAttributes));
    }


}

































