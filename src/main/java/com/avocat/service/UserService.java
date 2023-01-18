package com.avocat.service;

import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.BranchOfficeRepository;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Transactional
    public UserAppDto create(UserApp user) {

        if(user.getPrivileges().isEmpty()) {
            var privilege = privilegeRepository.findByName(PrivilegesTypes.ROLE_USER.name());
            user.setPrivileges(privilege);
        }
        return UserAppDto.from(userAppRepository.save(user));
    }

    @Transactional
    public UserAppDto update(UUID userId, UUID branchOfficeId, UserApp user) {

        var branchOfficeResult = branchOfficeRepository.findById(branchOfficeId)
                .orElseThrow(() -> new ResourceNotFoundException("Branch Office not found"));

        var userResult = userAppRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user id: " + user.getId() + " not found"));

        userResult.setUsername(user.getUsername());
        userResult.setBranchOffice(user.getBranchOffice());
        userResult.setPrivileges(user.getPrivileges());
        userResult.setBranchOffice(branchOfficeResult);

        return UserAppDto.from(userAppRepository.save(userResult));
    }
}
