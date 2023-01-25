package com.avocat.controller.privilege;

import com.avocat.persistence.entity.Privilege;
import com.avocat.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/branch-office/{branchOfficeId}/privileges", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('Privilege_WRITE')")
    @PostMapping
    public ResponseEntity<Privilege> create(@PathVariable("branchOfficeId") UUID id, @RequestBody Privilege obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.create(id, obj));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('Privilege_WRITE')")
    @PutMapping("/{id}")
    public ResponseEntity<Privilege> update(@PathVariable("id") UUID id, @RequestBody Privilege obj) {
        return ResponseEntity.ok().body(privilegeService.update(id, obj));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('Privilege_WRITE')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        privilegeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Privilege>> findAll(@PathVariable("branchOfficeId") UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privilege> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.findById(id));
    }
}
