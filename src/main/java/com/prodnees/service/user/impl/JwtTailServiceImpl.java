/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.service.user.impl;

import com.prodnees.dao.user.JwtTailDao;
import com.prodnees.domain.user.JwtTail;
import com.prodnees.domain.user.User;
import com.prodnees.service.user.JwtTailService;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class JwtTailServiceImpl implements JwtTailService {
    private static final String DEAD = "DE4D";
    private static final String FACE = "FAC3";
    private final JwtTailDao jwtTailDao;

    public JwtTailServiceImpl(JwtTailDao jwtTailDao) {
        this.jwtTailDao = jwtTailDao;
    }

    @Override
    public boolean existsByUserId(int userId) {
        return jwtTailDao.existsByUserId(userId);
    }

    @Override
    public JwtTail getByEmail(String email) {
        return jwtTailDao.getByEmail(email);
    }

    @Override
    public JwtTail getByUserId(int userId) {
        return jwtTailDao.getByUserId(userId);
    }

    /**
     * 0 1 2 3 4 5 6 7 8 9 A B C D E F
     *
     * @param user
     * @return
     */
    @Override
    public String generateAndUpdateNextTail(User user) {
        TextEncryptor textEncryptor = Encryptors.text(DEAD, FACE);
        String timeStamp = Instant.now().toString();
        final String newJwtTail = textEncryptor.encrypt(timeStamp);

        if (jwtTailDao.existsByUserId(user.getId())) {
            JwtTail jwtTail = jwtTailDao.getByUserId(user.getId())
                    .setTail(newJwtTail)
                    .setLastChangedDate(LocalDate.now());
            jwtTailDao.save(jwtTail);
        } else {
            jwtTailDao.save(new JwtTail()
                    .setUserId(user.getId())
                    .setEmail(user.getEmail())
                    .setTail(newJwtTail)
                    .setLastChangedDate(LocalDate.now()));
        }
        return newJwtTail;
    }

    @Override
    public boolean isValidTail(String email, String jwtTail) {
        return jwtTailDao.existsByEmailAndTail(email, jwtTail);

    }

}
