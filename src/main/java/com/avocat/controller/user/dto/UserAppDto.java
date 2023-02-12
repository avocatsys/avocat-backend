package com.avocat.controller.user.dto;

import com.avocat.controller.branchoffice.dto.BranchOfficeDto;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
public class UserAppDto {

    private UUID id;
    private String username;
    private Set<Privilege> privileges;
    private BranchOfficeDto branchOffice;

    private UserAppDto(UUID id, String username, Set<Privilege> privileges, BranchOfficeDto branchOffice) {
        this.id = id;
        this.username = username;
        this.privileges = privileges;
        this.branchOffice = branchOffice;
    }

    public static UserAppDto from(UserApp user) {
        return new UserAppDto(user.getId(), user.getUsername(), user.getPrivileges(), new BranchOfficeDto(user.getBranchOffice()));
    }
}
