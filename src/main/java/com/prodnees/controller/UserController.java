package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.dao.BlockedJwtDao;
import com.prodnees.dao.ForgotPasswordInfoDao;
import com.prodnees.dao.TempPasswordInfoDao;
import com.prodnees.domain.BlockedJwt;
import com.prodnees.domain.User;
import com.prodnees.dto.PasswordDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.model.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.prodnees.web.response.SuccessResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
public class UserController {

    private final BlockedJwtDao blockedJwtDao;
    private final UserAction userAction;
    private final UserValidator userValidator;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;

    public UserController(BlockedJwtDao blockedJwtDao, UserAction userAction,
                          UserValidator userValidator, TempPasswordInfoDao tempPasswordInfoDao,
                          ForgotPasswordInfoDao forgotPasswordInfoDao) {
        this.blockedJwtDao = blockedJwtDao;
        this.userAction = userAction;
        this.userValidator = userValidator;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
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
    @Transactional
    public ResponseEntity<?> updateTemporaryPassword(@Validated @RequestBody PasswordDto dto,
                                                     HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        User user = userAction.getById(userId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserModel userModel = userAction.save(user);
        forgotPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from ForgotPasswordInfo
        tempPasswordInfoDao.deleteByEmail(user.getEmail()); // remove row from TempPasswordInfo
        String jwt = userValidator.extractToken(servletRequest);
        blockedJwtDao.save(new BlockedJwt().setJwt(jwt).setUserId(user.getId()).setEmail(user.getEmail())); // add to BlockedJwt

        return configure(userModel);
    }

    public ResponseEntity<?> updatePassword(@Validated @RequestBody PasswordDto dto,
                                            HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        User user = userAction.getById(userId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserModel userModel = userAction.save(user);
        return configure(userModel);
    }

    @GetMapping("/users")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return configure(userAction.getModelById(userId));
    }


}
