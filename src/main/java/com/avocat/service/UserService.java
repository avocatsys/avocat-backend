package com.avocat.service;

import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.GroupRepository;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
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

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public UserAppDto create(UUID branchOfficeId, UserApp user) {
        user.setBranchOffice(branchOfficeService.getBranchOffice(branchOfficeId));
        user.setPrivileges(getPrivileges(user.getPrivileges()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return UserAppDto.from(userAppRepository.save(user));
    }

    @Transactional
    public UserAppDto update(UUID branchOfficeId, UserApp user) {

        var branchOfficeResult = branchOfficeService.getBranchOffice(branchOfficeId);

        var userResult = userAppRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user id: " + user.getId() + " not found"));

        if (user.getPrivileges().isEmpty() && userResult.getPrivileges().isEmpty()) {
            user.setPrivileges(privilegeRepository.findByName(PrivilegesTypes.ROLE_USER.name()));
        }

        userResult.setGroup(user.getGroup());
        userResult.setPrivileges(user.getPrivileges());
        userResult.setName(user.getName());
        userResult.setUsername(user.getUsername());
        userResult.setBranchOffice(branchOfficeResult);

        return UserAppDto.from(userAppRepository.save(userResult));
    }

    @Transactional
    public void delete(UUID userId) {
        userAppRepository.delete(getUserApp(userId));
    }

    public Page<UserAppDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return userAppRepository.findAllByBranchOffice_Customer_Id(branchOfficeId, pageable).map(UserAppDto::from);
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

    private Set<Privilege> getPrivileges(Set<Privilege> privilegeSet) {
        Set<Privilege> privileges = new HashSet<>();

        for(Privilege p : privilegeSet) {
            var result = privilegeRepository.findById(p.getId());
            privileges.add(result.get());
        }
        return privileges;
    }

    public Optional<UserApp> findByUsernameAndBranchOfficeAndCustomer_Id(String username, UUID customerId) {
        return userAppRepository.findByUsernameAndBranchOffice_Customer_Id(username, customerId);
    }
}
