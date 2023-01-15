package com.avocat.service;

import com.avocat.controller.authentication.dto.AccountCreatedDto;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

@Service
public class CreateAccountService {

    @Autowired
    private UserRepository userRepository;

    @Transient
    public AccountCreatedDto createAccount(UserApp user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return AccountCreatedDto.from(userRepository.save(user));
    }
}
