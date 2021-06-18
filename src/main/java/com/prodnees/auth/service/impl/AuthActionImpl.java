package com.prodnees.auth.service.impl;

import com.prodnees.auth.OtpUtil;
import com.prodnees.auth.dao.ForgotPasswordInfoDao;
import com.prodnees.auth.domain.ForgotPasswordInfo;
import com.prodnees.auth.service.AuthAction;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AuthActionImpl implements AuthAction {
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;
    private final LocalEmailService localEmailService;

    public AuthActionImpl(ForgotPasswordInfoDao forgotPasswordInfoDao,
                          LocalEmailService localEmailService) {
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
        this.localEmailService = localEmailService;
    }

    @Override
    public boolean authenticateWithForgotPasswordCredentials(String email, String password) {
        Optional<ForgotPasswordInfo> forgotPasswordInfoOptional = forgotPasswordInfoDao.findByEmail(email);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        forgotPasswordInfoOptional.ifPresentOrElse(forgotPasswordInfo -> {
            atomicBoolean.set(passwordEncoder.matches(password, forgotPasswordInfo.getPassword()));
        }, () -> atomicBoolean.set(false));
        return atomicBoolean.get();
    }

    @Override
    public void processForgotPassword(String email) {
        String generatedPassword = OtpUtil.generateRandomOtp(6);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        ForgotPasswordInfo forgotPasswordInfo = new ForgotPasswordInfo()
                .setEmail(email)
                .setPassword(passwordEncoder.encode(generatedPassword))
                .setCreatedDateTime(LocalDateTime.now());
        forgotPasswordInfoDao.save(forgotPasswordInfo);
        Map<String, Object> mailMap = new HashMap<>();

        mailMap.put(EmailPlaceHolders.TITLE, "Password Recovery");
        mailMap.put(EmailPlaceHolders.MESSAGE, String.format("Your temporary password for ProdNees is: [ %s ]", generatedPassword));
        mailMap.put(EmailPlaceHolders.PARA_ONE, "Please change your temporary password on your next login");
        try {
            localEmailService.sendTemplateEmail(email, "Password Recovery", mailMap);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
