package com.avocat.controller.authentication;

import com.avocat.controller.BaseTestController;
import com.avocat.controller.authentication.dto.AccountCreatedDto;
import com.avocat.persistence.entity.UserApp;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAccountControllerTests extends BaseTestController {

    @Test
    void givenNewUser_whenCreatedAccount_thenReturnHttpStatusCreated_201() throws Exception {

        var account = new UserApp.Builder(UUID.randomUUID() + "@owtest.com", "123").build();

        HttpEntity<UserApp> request = new HttpEntity<>(account);

        URI uri = new URI(host + port + "/avocat/v1/account");

        ResponseEntity<AccountCreatedDto> result = restTemplate.exchange(uri, HttpMethod.POST, request,
                AccountCreatedDto.class);

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }
}
