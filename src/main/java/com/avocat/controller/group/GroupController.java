package com.avocat.controller.group;

import com.avocat.controller.GenericController;
import com.avocat.persistence.entity.Group;
import com.avocat.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RequestMapping(path = "/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController extends GenericController<Group> {

    @Autowired
    private GenericService<Group> genericService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('GROUP_WRITE')")
    @Override
    public ResponseEntity<Group> create(@RequestBody Group obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genericService.create(obj));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('GROUP_WRITE')")
    @Override
    public ResponseEntity<Group> update(@PathVariable("id") UUID id, @RequestBody Group obj) {
        return  ResponseEntity.ok().body(genericService.update(id, obj));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('GROUP_WRITE')")
    @Override
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        genericService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
