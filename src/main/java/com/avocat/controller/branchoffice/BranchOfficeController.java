package com.avocat.controller.branchoffice;

import com.avocat.controller.branchoffice.dto.BranchOfficeDto;
import com.avocat.persistence.entity.BranchOffice;
import com.avocat.service.BranchOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PostMapping
    public ResponseEntity<BranchOffice> create(@PathVariable("customerId") UUID customerId, @Valid @RequestBody BranchOffice branchOffice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchOfficeService.create(customerId, branchOffice));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    @PutMapping
    public ResponseEntity<BranchOffice> update(@RequestBody BranchOffice branchOffice) {
        return ResponseEntity.ok().body(branchOfficeService.update(branchOffice));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_OWNER')")
    @DeleteMapping("/{branchOfficeId}")
    public ResponseEntity delete(@PathVariable("branchOfficeId") UUID id) {
        branchOfficeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<BranchOfficeDto>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(branchOfficeService.findAllByCustomer_Id(customerId, pageable));
    }

    @GetMapping("/{branchOfficeId}")
    public ResponseEntity<BranchOfficeDto> findById(@PathVariable("branchOfficeId") UUID branchOfficeId) {
        return ResponseEntity.status(HttpStatus.OK).body(branchOfficeService.findById(branchOfficeId));
    }
}
