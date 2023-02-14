package com.avocat.controller.authentication.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Getter
public class CredentialsDto {

    private String token;
    private UUID customerId;
    private UUID branchOfficeId;
    private String username;
    private String name;

    private CredentialsDto(String token, UUID customerId, UUID branchOfficeId, String username, String name) {
        this.token = token;
        this.customerId = customerId;
        this.branchOfficeId = branchOfficeId;
        this.username = username;
        this.name = name;
    }

    public static CredentialsDto create(String token, UUID customerId, UUID branchOfficeId, String username, String name) {
        return new CredentialsDto(token, customerId, branchOfficeId, username, name);
    }
}
