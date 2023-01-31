package com.avocat.controller.privilege;

import com.avocat.persistence.entity.Privilege;
import com.avocat.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('PRIVILEGE_WRITE')")
    @PostMapping
    public ResponseEntity<Privilege> create(@PathVariable("branchOfficeId") UUID id, @RequestBody Privilege obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.create(id, obj));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('PRIVILEGE_WRITE')")
    @PutMapping("/{id}")
    public ResponseEntity<Privilege> update(@PathVariable("id") UUID id, @RequestBody Privilege obj) {
        return ResponseEntity.ok().body(privilegeService.update(id, obj));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('PRIVILEGE_WRITE')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        privilegeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Privilege>> findAll(
            @PathVariable("branchOfficeId") UUID branchOfficeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.findAll(branchOfficeId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privilege> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.findById(id));
    }
}
