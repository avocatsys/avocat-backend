package com.avocat.controller.user.dto;

import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserAppDto {

    @JsonProperty("userId")
    private UUID id;

    @JsonProperty("username")
    private String username;

    private Set<Privilege> privileges = new HashSet<>();

    private UserAppDto(UUID id, String username, Set<Privilege> privileges) {
        this.id = id;
        this.username = username;
        this.privileges = privileges;
    }

    public static UserAppDto from(UserApp user) {
        return new UserAppDto(user.getId(), user.getUsername(), user.getPrivileges());
    }
}
