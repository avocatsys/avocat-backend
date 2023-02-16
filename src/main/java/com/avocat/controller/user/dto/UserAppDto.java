package com.avocat.controller.user.dto;

import com.avocat.controller.branchoffice.dto.BranchOfficeDto;
import com.avocat.persistence.entity.Group;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
public class UserAppDto {

    private UUID id;
    private String name;
    private String username;
    private Set<Privilege> privileges;
    private BranchOfficeDto branchOffice;
    private Group group;

    private UserAppDto(UUID id, String name, String username, Set<Privilege> privileges, BranchOfficeDto branchOffice, Group group) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.privileges = privileges;
        this.branchOffice = branchOffice;
        this.group = group;
    }

    public static UserAppDto from(UserApp user) {
        return new UserAppDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPrivileges(),
                new BranchOfficeDto(user.getBranchOffice()),
                user.getGroup());
    }
}
