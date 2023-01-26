package com.avocat.controller.branchoffice;

import com.avocat.controller.branchoffice.dto.BranchOfficeDto;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.service.BranchOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/customer/{customerId}/branch-offices", produces = MediaType.APPLICATION_JSON_VALUE)
public class BranchOfficeController {

    @Autowired
    private BranchOfficeService branchOfficeService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<BranchOffice> create(@PathVariable("customerId") UUID customerId, @Valid @RequestBody BranchOffice branchOffice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchOfficeService.create(customerId, branchOffice));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BranchOffice> update(@PathVariable("id") UUID customerId, @RequestBody BranchOffice branchOffice) {
        return ResponseEntity.ok().body(branchOfficeService.update(customerId, branchOffice));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        branchOfficeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BranchOfficeDto>> findAll(@PathVariable("customerId") UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchOfficeService.findAllByCustomer_Id(customerId));
    }

    @GetMapping("/{branchOfficeId}")
    public ResponseEntity<BranchOfficeDto> findById(@PathVariable("branchOfficeId") UUID branchOfficeId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchOfficeService.findById(branchOfficeId));
    }
}
