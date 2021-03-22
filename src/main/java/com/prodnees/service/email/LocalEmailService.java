package com.prodnees.service.email;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface LocalEmailService {

    void send(NeesPosEmail email);

    void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException;

    String getSystemEmailAddress();

    void sendTemplateEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException, UnsupportedEncodingException;

}
