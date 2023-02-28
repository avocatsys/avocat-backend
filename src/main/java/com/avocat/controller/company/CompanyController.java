package com.avocat.controller.company;

import com.avocat.controller.company.dto.CompanyDto;
import com.avocat.persistence.entity.Company;
import com.avocat.service.CompanyService;
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
import java.util.UUID;

@RequestMapping(path = "/v1/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER', 'ROLE_COMPANY')")
    @PostMapping("/branch-office/{branchOfficeId}/companies")
    public ResponseEntity<CompanyDto> create(
            @PathVariable("customerId") UUID customerId,
            @PathVariable("branchOfficeId") UUID branchOfficeId,
            @RequestBody @Valid CompanyDto companyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.create(customerId, branchOfficeId, companyDto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER', 'ROLE_COMPANY')")
    @PutMapping("/companies")
    public ResponseEntity<CompanyDto> update(
            @PathVariable("customerId") UUID customerId,
            @RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.update(customerId, company));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER', 'ROLE_COMPANY')")
    @DeleteMapping("/companies/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/companies")
    public ResponseEntity<Page<CompanyDto>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll(customerId, pageable));
    }

    @GetMapping("/companies/{companyId}")
    public ResponseEntity<CompanyDto> findById(@PathVariable("companyId") UUID companyId) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.findById(companyId));
    }
}
