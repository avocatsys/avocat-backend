package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrivilegeService extends GenericService<Privilege> {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public Privilege update(UUID id, Privilege obj) {

        var result = privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("rerouce not found"));

        result.setName(obj.getName());

        return privilegeRepository.save(result);
    }
}
