package com.prodnees.service;

import com.prodnees.dao.UserDao;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {
    private final UserDao userDao;

    public LoginUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        com.prodnees.domain.User user = userDao.findByEmail(userName);

        if (user.getEmail().equals(userName)) {
            return User.withUsername(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .disabled(!user.isEnabled()).build();
        } else {
            throw new UsernameNotFoundException("user not found with username : ${username}");
        }
    }
}
