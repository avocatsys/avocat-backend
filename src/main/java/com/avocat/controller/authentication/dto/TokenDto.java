package com.avocat.controller.authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenDto {

    @JsonProperty("access_token")
    private final String token;

    private TokenDto(String token) {
        this.token = token;
    }

    public static TokenDto create(final String token) {
        return new TokenDto(token);
    }
}
