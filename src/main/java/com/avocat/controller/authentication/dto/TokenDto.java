package com.avocat.controller.authentication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenDto {

    private String token;

    private TokenDto(String token) {
        this.token = token;
    }

    public static TokenDto create(final String token) {
        return new TokenDto(token);
    }
}
