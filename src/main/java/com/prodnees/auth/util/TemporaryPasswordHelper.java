/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.util;


import com.prodnees.auth.service.SignupService;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * <p>to {@link SignupService} class along with {@link #emailTemporaryPassword} method later</p>
 * <i>Created this class to remove duplication</i>
 */
public class TemporaryPasswordHelper {
    private final LocalEmailService emailService;

    public TemporaryPasswordHelper(LocalEmailService emailService) {
        this.emailService = emailService;
    }

    public void emailTemporaryPassword(String email, String tempPassword, int companyId) {
        Map<String, Object> newUserLoginDetails = new HashMap<>();
        newUserLoginDetails.put(EmailPlaceHolders.TITLE, "New Login Details");
        newUserLoginDetails.put(EmailPlaceHolders.PRE_HEADER, String.format("Temporary password created for: %s", email));
        newUserLoginDetails.put(EmailPlaceHolders.MESSAGE, String.format("Email: %s ,Temporary password: %s", email, tempPassword));
        newUserLoginDetails.put(EmailPlaceHolders.PARA_ONE, String.format("Your Company's Id: %d", companyId));
        newUserLoginDetails.put(EmailPlaceHolders.PARA_TWO, "Please change your password in your next login, https://www.atozmanage.com/");

        try {
            emailService.sendTemplateEmail(email, "New Login Details", newUserLoginDetails);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
