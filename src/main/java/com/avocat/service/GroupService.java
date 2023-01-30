package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.entity.Group;
import com.avocat.persistence.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService<T extends AuditEntity> {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Transactional
    public Group create(UUID id, Group group) {
        var branchOffice = branchOfficeService.getBranchOffice(id);
        group.setBranchOffice(branchOffice.getId());
        return groupRepository.save(group);
    }

    @Transactional
    public Group update(UUID id, Group group) {
        var result = getGroup(id);
        return groupRepository.save(Group.from(group, result));
    }

    @Transactional
    public void delete(UUID id) {
        groupRepository.delete(getGroup(id));
    }

    public Page<Group> findAll(UUID branchOfficeId, Pageable pageable) {
        return groupRepository.findAllByBranchOffice(branchOfficeId, pageable);
    }

    public Group findById(UUID id) {
        return getGroup(id);
    }

    private Group getGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
