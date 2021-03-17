package com.prodnees.service.email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface LocalEmailService {

    interface TemplatePlaceHolders {
        String TITLE = "title";
        String PRE_HEADER = "preHeader";
        String RECIPIENT = "recipient";
        String MESSAGE = "message";
        String PARA_ONE = "para_1";
        String PARA_TWO = "para_2";
        String SENDER = "sender";
    }

    void send(NeesPosEmail email);

    void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException;

    String getSystemEmailAddress();

    void sendTemplateEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException, UnsupportedEncodingException;

}
