package com.prodnees.action.impl;

import com.prodnees.action.UserAction;
import com.prodnees.dao.TempPasswordInfoDao;
import com.prodnees.domain.TempPasswordInfo;
import com.prodnees.domain.User;
import com.prodnees.domain.UserAttributes;
import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.model.UserModel;
import com.prodnees.service.UserAttributesService;
import com.prodnees.service.UserService;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.util.OtpUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    @Transactional
    public UserModel save(UserRegistrationDto dto) {
        String generatedPassword = OtpUtil.generateRandomOtp(6);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userService.save(new User()
                .setEmail(dto.getEmail())
                .setPassword(passwordEncoder.encode(generatedPassword))
                .setEnabled(true)
                .setRole("ADMIN"));
        sendInitialPassword(user.getEmail(), generatedPassword);

        UserAttributes attributes = new UserAttributes();
        attributes.setUserId(user.getId())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setEmail(user.getEmail());
        userAttributesService.save(attributes);
        tempPasswordInfoDao.save(new TempPasswordInfo().setEmail(user.getEmail()).setCreatedDateTime(LocalDateTime.now()));
        return mapToModel(user);
    }

    @Override
    public UserModel save(User user) {
        return mapToModel(userService.save(user));
    }

    @Async
    void sendInitialPassword(String email, String initialPassword) {
        Map<String, Object> mailMap = new HashMap<>();
        mailMap.put(LocalEmailService.TemplatePlaceHolders.TITLE, "Temporary Password");
        mailMap.put(LocalEmailService.TemplatePlaceHolders.PRE_HEADER, String.format("New email from %s. ", "ProdNees"));
        mailMap.put(LocalEmailService.TemplatePlaceHolders.MESSAGE, String.format("Your temporary password for ProdNees is: %s ", initialPassword));
        mailMap.put(LocalEmailService.TemplatePlaceHolders.PARA_ONE, "Please change your temporary password on your next login");

        try {
            localEmailService.sendTemplateEmail(email, "Your Temporary Password", mailMap);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public UserModel getModelById(int id) {
        return mapToModel(userService.getById(id));
    }

    @Override
    public UserModel getModelByEmail(String email) {
        return mapToModel(userService.getByEmail(email));
    }

    @Override
    public User getById(int id) {
        return userService.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userService.getByEmail(email);
    }

    UserModel mapToModel(User user) {
        UserAttributes attributes = userAttributesService.getByUserId(user.getId());
        UserModel model = new UserModel();
        return model.setId(user.getId())
                .setEmail(user.getEmail())
                .setRole(user.getRole())
                .setEnabled(user.isEnabled())
                .setFirstName(attributes.getFirstName())
                .setLastName(attributes.getLastName())
                .setPhoneNumber(attributes.getPhoneNumber())
                .setAddress(attributes.getAddress());
    }
}
