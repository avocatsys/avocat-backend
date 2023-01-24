package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.entity.Group;
import com.avocat.persistence.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class GroupService<T extends AuditEntity> {

    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public Group create(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public Group update(UUID id, Group group) {
        Group groupResult = getGroup(id);
        return groupRepository.save(Group.from(group, groupResult));
    }

    @Transactional
    public void delete(UUID id) {
        groupRepository.delete(getGroup(id));
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(UUID id) {
        return getGroup(id);
    }

    private Group getGroup(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }
}
