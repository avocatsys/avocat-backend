package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Transactional
    public Privilege create(UUID id, Privilege privilege) {
        var branchOffice = branchOfficeService.getBranchOffice(id);
        privilege.setBranchOffice(branchOffice.getId());
        return privilegeRepository.save(privilege);
    }

    @Transactional
    public Privilege update(UUID id, Privilege privilege) {
        var result = getPrivilege(id);
        return privilegeRepository.save(Privilege.from(privilege, result));
    }

    @Transactional
    public void delete(UUID id) {
        privilegeRepository.delete(getPrivilege(id));
    }

    public List<Privilege> findAll() {
        return privilegeRepository.findAll();
    }

    public Privilege findById(UUID id) {
        return getPrivilege(id);
    }

    private Privilege getPrivilege(UUID id) {
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
