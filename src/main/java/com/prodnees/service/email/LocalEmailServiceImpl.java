package com.prodnees.service.email;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Service
@EnableAsync
public class LocalEmailServiceImpl implements LocalEmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public LocalEmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine thymeleafTemplateEngine) {
        this.javaMailSender = javaMailSender;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void send(NeesPosEmail email) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getTo()));
            mimeMessage.setFrom(new InternetAddress(email.getFrom()));
            mimeMessage.setSubject(email.getSubject());
            mimeMessage.setText(email.getText());
        };
        javaMailSender.send(mimeMessagePreparator);
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom(getSystemEmailAddress(), "ProdNees");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);

    }

    @Override
    public String getSystemEmailAddress() {
        return "support@neesum.com";
    }

    /**
     * placeholders contained in  mail_template.html are:
     * <p>title</p>
     * <p>preHeader</p>
     * <p>recipient</p>
     * <p>message</p>
     * <p>para_1</p>
     * <p>para_2</p>
     * <p>sender</p>
     * @param to
     * @param subject
     * @param templateModel
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    @Override
    @Async
    public void sendTemplateEmail(String to, String subject, Map<String, Object> templateModel) throws MessagingException, UnsupportedEncodingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        String htmlBody = thymeleafTemplateEngine.process("mail_template.html", thymeleafContext);
        sendHtmlMessage(to, subject, htmlBody);
    }


}
