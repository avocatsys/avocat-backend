package com.avocat.controller.authentication;

import com.avocat.controller.authentication.dto.AccountCreatedDto;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CreateAccountController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<AccountCreatedDto> createAccount(@RequestBody UserApp user) {
        var result = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(AccountCreatedDto.from(result));
    }
}
