package com.prodnees.filter;

import com.prodnees.service.jwt.JwtService;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class RequestValidatorImpl implements RequestValidator {
    private final JwtService jwtService;

    public RequestValidatorImpl(JwtService jwtService) {
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

    @Override
    public boolean hasUsedTempPassword(HttpServletRequest servletRequest) {
        return jwtService.hasUsedTempPassword(extractToken(servletRequest));
    }

    /**
     * CHARACTER CLASSES
     * <p>[abc]	        simple, matches a or b, or c</p>
     * <p>[\^abc]	        negation, matches everything except a, b, or c</p>
     * <p>[a-c]	        range, matches a or b, or c</p>
     * <p>[a-c[f-h]]	    union, matches a, b, c, f, g, h</p>
     * <p>[a-c&&[b-c]]	    intersection, matches b or c</p>
     * <p>[a-c&&[\^b-c]]	subtraction, matches only a</p>
     * <p>**********************************************</p>
     * <p>PREDEFINED CHARACTER CLASSES</p>
     * <p>.	Any character</p>
     * <p>\d	A digit: [0-9]</p>
     * <p>\D	A non-digit: [\^0-9]</p>
     * <p>\s	A whitespace character: [ \t\n\x0B\f\r]</p>
     * <p>\S	A non-whitespace character: [\^\s]</p>
     * <p>\w	A word character: [a-zA-Z_0-9]</p>
     * <p>\W	A non-word character: [\^\w]</p>
     *
     * @param email
     * @return boolean
     */
    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}


