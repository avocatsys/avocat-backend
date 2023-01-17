package com.avocat.service;

import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class UserService {

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
}
