package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    public Page<Privilege> findAll(Pageable pageable) {
        return privilegeRepository.findAll(pageable);
    }

    public Privilege findById(UUID id) {
        return getPrivilege(id);
    }

    private Privilege getPrivilege(UUID id) {
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
