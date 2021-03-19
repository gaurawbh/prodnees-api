package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.dao.BlockedJwtDao;
import com.prodnees.dao.ForgotPasswordInfoDao;
import com.prodnees.dao.TempPasswordInfoDao;
import com.prodnees.domain.BlockedJwt;
import com.prodnees.domain.User;
import com.prodnees.domain.UserAttributes;
import com.prodnees.dto.SecPasswordDto;
import com.prodnees.dto.TempPasswordDto;
import com.prodnees.dto.UserAttributesDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.model.UserModel;
import com.prodnees.service.UserAttributesService;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.util.ValidatorUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.prodnees.web.response.SuccessResponse.configure;

/**
 * todo in this controller
 * <p>change password</p>
 * <p>update UserAttributes</p>
 */
@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class UserController {

    private final BlockedJwtDao blockedJwtDao;
    private final UserAction userAction;
    private final UserValidator userValidator;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;
    private final AssociatesService associatesService;
    private final UserAttributesService userAttributesService;

    public UserController(BlockedJwtDao blockedJwtDao, UserAction userAction,
                          UserValidator userValidator, TempPasswordInfoDao tempPasswordInfoDao,
                          ForgotPasswordInfoDao forgotPasswordInfoDao, AssociatesService associatesService,
                          UserAttributesService userAttributesService) {
        this.blockedJwtDao = blockedJwtDao;
        this.userAction = userAction;
        this.userValidator = userValidator;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
        this.associatesService = associatesService;
        this.userAttributesService = userAttributesService;
    }

    /**
     * @param servletRequest
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUser(HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
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
        int userId = userValidator.extractUserId(servletRequest);
        User user = userAction.getById(userId);
        Assert.isTrue(passwordEncoder.matches(user.getPassword(), dto.getOldPassword()), "incorrect oldPassword, please try again");
        Assert.isTrue(dto.getNewPassword().equals(dto.getRepeatNewPassword()), "newPassword and repeatNewPassword do not match");
        Assert.isTrue(!dto.getOldPassword().equals(dto.getNewPassword()), "password not updated, your oldPassword and newPassword are same");
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        UserModel userModel = userAction.save(user);
        String jwt = userValidator.extractToken(servletRequest);
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
        int userId = userValidator.extractUserId(servletRequest);
        User user = userAction.getById(userId);
        Assert.isTrue(tempPasswordInfoDao.existsByEmail(user.getEmail()), "temporary password was not requested for you");// check that the temporary password was requested.
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserModel userModel = userAction.save(user);
        String jwt = userValidator.extractToken(servletRequest);
        blockedJwtDao.save(new BlockedJwt().setJwt(jwt).setUserId(user.getId()).setEmail(user.getEmail())); // add to BlockedJwt
        forgotPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from ForgotPasswordInfo
        tempPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from TempPasswordInfo

        return configure(userModel);
    }

    @PutMapping("/user")
    public ResponseEntity<?> update(@Validated @RequestBody UserAttributesDto dto,
                                    HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        UserAttributes userAttributes = userAttributesService.getByUserId(userId);
        userAttributes.setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setAddress(ValidatorUtil.ifValidOrElse(dto.getAddress(), userAttributes.getAddress()))
                .setPhoneNumber(ValidatorUtil.ifValidOrElse(dto.getPhoneNumber(), userAttributes.getPhoneNumber()));

        return configure(userAttributesService.save(userAttributes));
    }


    @GetMapping("/associates")
    public ResponseEntity<?> getAssociates(@RequestParam Optional<Integer> id,
                                           HttpServletRequest servletRequest) {
        int adminId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> modelAtomicReference = new AtomicReference<>();

        id.ifPresentOrElse(integer -> {
            Assert.isTrue(associatesService.findByAdminIdAndUserId(adminId, integer).isPresent(), APIErrors.USER_NOT_FOUND.getMessage());
            modelAtomicReference.set(userAction.getModelById(integer));
        }, () -> modelAtomicReference.set(userAction.getAllAssociates(adminId)));

        return configure(modelAtomicReference.get());
    }

}

































