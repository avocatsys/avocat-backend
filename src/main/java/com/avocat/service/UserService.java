package com.avocat.service;

import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Transactional
    public UserAppDto create(UUID branchOfficeId, UserApp user) {
        user.setBranchOffice(branchOfficeService.getBranchOffice(branchOfficeId));
        user.setPrivileges(getDefaultPrivilege(user));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return UserAppDto.from(userAppRepository.save(user));
    }

    @Transactional
    public UserAppDto update(UUID userId, UUID branchOfficeId, UserApp user) {

        var branchOfficeResult = branchOfficeService.getBranchOffice(branchOfficeId);

        var userResult = userAppRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user id: " + user.getId() + " not found"));

        if (user.getPrivileges().isEmpty()) {
            user.setPrivileges(getDefaultPrivilege(user));
        } else {
            userResult.setPrivileges(user.getPrivileges());
        }

        userResult.setUsername(user.getUsername());
        userResult.setBranchOffice(branchOfficeResult);

        return UserAppDto.from(userAppRepository.save(userResult));
    }

    @Transactional
    public void delete(UUID userId) {
        userAppRepository.delete(getUserApp(userId));
    }

    public Page<UserAppDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return userAppRepository.findAllByBranchOffice_Id(branchOfficeId, pageable).map(UserAppDto::from);
    }

    public UserAppDto findById(UUID userId) {
        return UserAppDto.from(getUserApp(userId));
    }

    public UserApp findByUsername(String email) {
        return userAppRepository.findByUsername(email)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    public Optional<UserApp> findByUsernameAndBranchOfficeId(String email, UUID branchOfficeId) {
        return userAppRepository.findByUsernameAndBranchOffice_Id(email, branchOfficeId);
    }

    private UserApp getUserApp(UUID id) {
        return userAppRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    private Set<Privilege> getDefaultPrivilege(UserApp user) {
        return privilegeRepository.findByName(PrivilegesTypes.ROLE_USER.name());
    }
}
