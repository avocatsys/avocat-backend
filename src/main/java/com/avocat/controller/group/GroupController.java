package com.avocat.controller.group;

import com.avocat.persistence.entity.Group;
import com.avocat.persistence.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @PostMapping
    public ResponseEntity<Group> create(@RequestBody Group group) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupRepository.save(group));
    }
}
