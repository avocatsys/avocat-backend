package com.avocat.controller.branchoffice;

import com.avocat.persistence.entity.BranchOffice;
import com.avocat.service.BranchOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/customer/{customerId}/branch-offices", produces = MediaType.APPLICATION_JSON_VALUE)
public class BranchOfficeController {

    @Autowired
    private BranchOfficeService branchOfficeService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('GROUP_WRITE')")
    @PostMapping
    public ResponseEntity<BranchOffice> create(@PathVariable("branchOfficeId") UUID customerId, @Valid @RequestBody BranchOffice branchOffice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchOfficeService.create(customerId, branchOffice));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('GROUP_WRITE')")
    @PutMapping("/{id}")
    public ResponseEntity<BranchOffice> update(@PathVariable("id") UUID customerId, @RequestBody BranchOffice branchOffice) {
        return ResponseEntity.ok().body(branchOfficeService.update(customerId, branchOffice));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('GROUP_WRITE')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        branchOfficeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /*
    @GetMapping
    public ResponseEntity<List<BranchOffice>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(branchOfficeService.findAll());
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<BranchOffice> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(branchOfficeService.findById(id));
    }
}
