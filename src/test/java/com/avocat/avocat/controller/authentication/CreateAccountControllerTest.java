package com.avocat.avocat.controller.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.avocat.controller.authentication.dto.AccountCreatedDto;
import com.avocat.persistence.entity.UserApp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CreateAccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public final String host = "http://localhost:";

    @Test
    void givenNewUser_whenCreatedAccount_thenReturnHttpStatusCreated_201() throws Exception {

        var account = new UserApp.Builder(UUID.randomUUID() + "@owtest.com", "12345678").build();

        HttpEntity<UserApp> request = new HttpEntity<>(account);

        URI uri = new URI(host + port + "/avocat/account");

        ResponseEntity<AccountCreatedDto> result = this.restTemplate.exchange(uri, HttpMethod.POST, request,
                AccountCreatedDto.class);

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }
}
