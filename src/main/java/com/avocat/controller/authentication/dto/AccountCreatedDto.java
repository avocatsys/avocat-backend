package com.avocat.controller.authentication.dto;

import com.avocat.persistence.entity.UserApp;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class AccountCreatedDto {

    @JsonProperty("user_id")
    private final UUID id;

    @JsonProperty("username")
    private final String username;

    private AccountCreatedDto(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static AccountCreatedDto from(UserApp userApp) {
        return new AccountCreatedDto(userApp.getId(), userApp.getUsername());
    }
}
