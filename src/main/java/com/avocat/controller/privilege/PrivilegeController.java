package com.avocat.controller.privilege;

import com.avocat.controller.GenericController;
import com.avocat.persistence.entity.Privilege;
import com.avocat.service.GenericService;
import com.avocat.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

//@PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('PRIVILEGE_WRITE')")
@RequestMapping(path = "/v1/privileges", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrivilegeController extends GenericController<Privilege> {

    @Autowired
    private PrivilegeService privilegeService;

    @Override
    public ResponseEntity<Privilege> create(Privilege obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(privilegeService.create(obj));
    }

    @Override
    public ResponseEntity<Privilege> update(UUID id, Privilege obj) {
        return ResponseEntity.ok().body(privilegeService.update(id, obj));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        privilegeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
