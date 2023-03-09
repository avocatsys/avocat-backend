package com.avocat.controller.group;

import com.avocat.persistence.entity.Group;
import com.avocat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/branch-office/{branchOfficeId}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('GROUP_WRITE')")
    @PostMapping
    public ResponseEntity<Group> create(@PathVariable("branchOfficeId") UUID branchOfficeId, @RequestBody Group obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.create(branchOfficeId, obj));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('GROUP_WRITE')")
    @PutMapping
    public ResponseEntity<Group> update(@RequestBody Group group) {
        return ResponseEntity.ok().body(groupService.update(group));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('GROUP_WRITE')")
    @DeleteMapping("/{groupId}")
    public ResponseEntity delete(@PathVariable("groupId") UUID groupId) {
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Group>> findAll(
            @PathVariable("branchOfficeId") UUID branchOfficeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findAll(branchOfficeId, pageable));
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<Group> findById(@PathVariable("groupId") UUID groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findById(groupId));
    }
}
