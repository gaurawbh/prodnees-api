package com.prodnees.action.impl;

import com.prodnees.action.UserAction;
import com.prodnees.dao.TempPasswordInfoDao;
import com.prodnees.domain.TempPasswordInfo;
import com.prodnees.domain.User;
import com.prodnees.domain.UserAttributes;
import com.prodnees.domain.rels.Associates;
import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.model.AssociateModel;
import com.prodnees.model.UserModel;
import com.prodnees.service.UserAttributesService;
import com.prodnees.service.UserService;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.AssociatesService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@EnableAsync
public class UserActionImpl implements UserAction {
    private final UserService userService;
    private final UserAttributesService userAttributesService;
    private final LocalEmailService localEmailService;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final AssociatesService associatesService;

    public UserActionImpl(UserService userService,
                          UserAttributesService userAttributesService,
                          LocalEmailService localEmailService,
                          TempPasswordInfoDao tempPasswordInfoDao,
                          AssociatesService associatesService) {
        this.userService = userService;
        this.userAttributesService = userAttributesService;
        this.localEmailService = localEmailService;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.associatesService = associatesService;
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
        return mapToUserModel(user);
    }

    @Override
    public UserModel save(User user) {
        return mapToUserModel(userService.save(user));
    }

    @Async
    void sendInitialPassword(String email, String initialPassword) {
        Map<String, Object> mailMap = new HashMap<>();
        mailMap.put(LocalEmailService.PlaceHolders.TITLE, "Temporary Password");
        mailMap.put(LocalEmailService.PlaceHolders.MESSAGE, String.format("Your temporary password for ProdNees is: %s ", initialPassword));
        mailMap.put(LocalEmailService.PlaceHolders.PARA_ONE, "Please change your temporary password on your next login");

        try {
            localEmailService.sendTemplateEmail(email, "Your Temporary Password", mailMap);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public UserModel getModelById(int id) {
        return mapToUserModel(userService.getById(id));
    }

    @Override
    public AssociateModel getAssociateById(int id) {
        return mapToAssociateModel(getById(id));
    }

    @Override
    public AssociateModel getAssociateByEmail(String email) {
        return mapToAssociateModel(getByEmail(email));
    }

    @Override
    public UserModel getModelByEmail(String email) {
        return mapToUserModel(userService.getByEmail(email));
    }

    @Override
    public User getById(int id) {
        return userService.getById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userService.getByEmail(email);
    }

    @Override
    public List<AssociateModel> getAllAssociates(int adminId) {
        List<Associates> associatesList = associatesService.getAllByAdminId(adminId);
        List<AssociateModel> associateModelList = new ArrayList<>();
        associatesList.forEach(associates -> associateModelList.add(mapToAssociateModel(getById(associates.getAssociateId()))));
        return associateModelList;
    }

    UserModel mapToUserModel(User user) {
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

    AssociateModel mapToAssociateModel(User user) {
        UserAttributes attributes = userAttributesService.getByUserId(user.getId());
        AssociateModel model = new AssociateModel();
        return model.setId(user.getId())
                .setEmail(user.getEmail())
                .setFirstName(attributes.getFirstName())
                .setLastName(attributes.getLastName())
                .setPhoneNumber(attributes.getPhoneNumber())
                .setAddress(attributes.getAddress());
    }
}
