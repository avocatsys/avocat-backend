package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.LegalBranch;
import com.avocat.persistence.repository.process.LegalBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE', 'LAWYER_READ')")
@RequestMapping(path = "/v1/customer/{customerId}/legal-branches", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LegalBranchController {

    @Autowired
    private LegalBranchRepository legalBranchRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<LegalBranch> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody LegalBranch legalBranch) {
        legalBranch.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(legalBranchRepository.save(legalBranch));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<LegalBranch> update(@PathVariable("customerId") UUID customerId, @RequestBody LegalBranch legalBranch) {
        return ResponseEntity.ok().body(legalBranchRepository.save(legalBranch));
    }

    @DeleteMapping("/{legalBranchId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("legalBranchId") UUID legalBranchId) {
        legalBranchRepository.deleteById(legalBranchId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<LegalBranch>> findAll(
            @PathVariable("legalBranchId") UUID legalBranchId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(legalBranchRepository.findAllByCustomerId(legalBranchId, pageable));
    }

    @GetMapping("/{legalBranchId}")
    public ResponseEntity<LegalBranch> findById(@PathVariable("legalBranchId") UUID legalBranchId,
                                                     @PathVariable("customerId") UUID customerId) {
        var result = legalBranchRepository.findByIdAndCustomerId(legalBranchId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
