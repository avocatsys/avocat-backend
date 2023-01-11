package com.avocat.controller.authentication.dto;

import com.avocat.persistence.entity.UserApp;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserCreatedDto {

    @JsonProperty("user_id")
    private final UUID id;

    @JsonProperty("username")
    private final String username;

    private UserCreatedDto(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserCreatedDto from(UserApp userApp) {
        return new UserCreatedDto(userApp.getId(), userApp.getUsername());
    }
}
