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

import java.util.UUID;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
@RestController
@RequestMapping(path = "/v1/branch-office/{branchOfficeId}/privileges", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrivilegeController {

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping
    public ResponseEntity<Page<Privilege>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.findAll(pageable));
    }

    @GetMapping("/{privilegeId}")
    public ResponseEntity<Privilege> findById(@PathVariable("privilegeId") UUID privilegeId) {
        return ResponseEntity.status(HttpStatus.OK).body(privilegeService.findById(privilegeId));
    }
}
