package com.prodnees.action.impl;

import com.prodnees.action.AuthAction;
import com.prodnees.dao.ForgotPasswordInfoDao;
import com.prodnees.domain.ForgotPasswordInfo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AuthActionImpl implements AuthAction {
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;

    public AuthActionImpl(ForgotPasswordInfoDao forgotPasswordInfoDao) {
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
    }

    @Override
    public boolean authenticateWithForgotPasswordCredentials(String email, String password) {
        Optional<ForgotPasswordInfo> forgotPasswordInfoOptional = forgotPasswordInfoDao.findByEmail(email);
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        forgotPasswordInfoOptional.ifPresentOrElse(forgotPasswordInfo -> {
            atomicBoolean.set(passwordEncoder.matches(forgotPasswordInfo.getEmail(), password));
        }, () -> atomicBoolean.set(false));
        return atomicBoolean.get();
    }
}
