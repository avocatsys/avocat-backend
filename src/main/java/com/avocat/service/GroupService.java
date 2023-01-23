package com.avocat.service;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.AuditEntity;
import com.avocat.persistence.entity.Group;
import com.avocat.persistence.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GroupService<T extends AuditEntity> extends GenericService<T> {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public T update(UUID id, T obj) {

        var groupResult = groupRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("resource not found"));

        var groupUpdated = Group.from((Group) obj, groupResult);

        return (T) groupRepository.save(groupUpdated);
    }
}
