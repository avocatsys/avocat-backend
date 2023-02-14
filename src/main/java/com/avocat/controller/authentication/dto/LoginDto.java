package com.avocat.controller.authentication.dto;

import lombok.Getter;

@Getter
public class LoginDto {
    private String username;
    private String password;

    public LoginDto(){}

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
