package com.prodnees.filter;

import com.prodnees.service.jwt.JwtService;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class UserValidatorImpl implements UserValidator {
    private final JwtService jwtService;

    public UserValidatorImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    public boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        try {
            Long.parseLong(phoneNumber);
            return isOfValidLength(phoneNumber);
        } catch (Exception e) {
            return false;
        }
    }

    public int extractUserId(HttpServletRequest request) {
        return jwtService.extractUserId(extractToken(request));
    }

    @Override
    public String extractUserEmail(HttpServletRequest request) {
        return jwtService.extractUsername(extractToken(request));
    }


    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(7);
    }

    private boolean isOfValidLength(String phoneNumber) {
        return phoneNumber.length() > 6;
    }

    public LocalDateTime extractTokenExpiryDatetime(HttpServletRequest request) {
        Date date = jwtService.extractTokenExpiryDatetime(extractToken(request));
        Jsr310JpaConverters.LocalDateTimeConverter localDateTimeConverter = new Jsr310JpaConverters.LocalDateTimeConverter();
        return localDateTimeConverter.convertToEntityAttribute(date);
    }


    public String extractUserRole(HttpServletRequest servletRequest) {
        return jwtService.extractUserRole(extractToken(servletRequest));
    }

    @Override
    public boolean hasUsedTempPassword(HttpServletRequest servletRequest) {
            return jwtService.hasUsedTempPassword(extractToken(servletRequest));
    }

    /**
     * CHARACTER CLASSES
     * [abc]	        simple, matches a or b, or c
     * [\^abc]	        negation, matches everything except a, b, or c
     * [a-c]	        range, matches a or b, or c
     * [a-c[f-h]]	    union, matches a, b, c, f, g, h
     * [a-c&&[b-c]]	    intersection, matches b or c
     * [a-c&&[\^b-c]]	subtraction, matches only a
     * **********************************************
     * PREDEFINED CHARACTER CLASSES
     * .	Any character
     * \d	A digit: [0-9]
     * \D	A non-digit: [\^0-9]
     * \s	A whitespace character: [ \t\n\x0B\f\r]
     * \S	A non-whitespace character: [\^\s]
     * \w	A word character: [a-zA-Z_0-9]
     * \W	A non-word character: [\^\w]
     *
     * @param email
     * @return boolean
     */
    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
//        String regex_1 = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public boolean isSecureRequest(String url) {
        return url.startsWith("/secure");
    }


}


