package com.avocat.controller.generic;

import com.avocat.persistence.entity.AuditEntity;
import com.avocat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public abstract class GenericController<T extends AuditEntity> {

    @Autowired
    private GroupService<T> groupService;

    @PostMapping
    public abstract ResponseEntity<T> create(@RequestBody T obj);

    @PutMapping("/{id}")
    public abstract ResponseEntity<T> update(@PathVariable("id") UUID uuid, @RequestBody T obj);

    @DeleteMapping("/{id}")
    public abstract ResponseEntity<Void> delete(@PathVariable("id") UUID id);

    @PreAuthorize("hasRole('{ ROLE_ADMIN, ROLE_USER }')")
    @GetMapping
    public ResponseEntity<List<T>> findAll() {//todo trocar para pageble
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findAll());
    }

    @PreAuthorize("hasRole('{ ROLE_ADMIN, ROLE_USER }')")
    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findById(id));
    }
}
