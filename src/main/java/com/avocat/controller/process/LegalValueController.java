package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.LegalValue;
import com.avocat.persistence.repository.process.LegalValueRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/legal-values", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class LegalValueController {

    @Autowired
    private LegalValueRepository legalValueRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<LegalValue> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody LegalValue legalValue) {
        legalValue.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(legalValueRepository.save(legalValue));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<LegalValue> update(@PathVariable("customerId") UUID customerId, @RequestBody LegalValue legalValue) {
        return ResponseEntity.ok().body(legalValueRepository.save(legalValue));
    }

    @DeleteMapping("/{legalValueId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("legalValueId") UUID legalValueId) {
        legalValueRepository.deleteById(legalValueId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<LegalValue>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(legalValueRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{legalValueId}")
    public ResponseEntity<LegalValue> findById(@PathVariable("legalValueId") UUID legalValueId,
                                                @PathVariable("customerId") UUID customerId) {
        var result = legalValueRepository.findByIdAndCustomerId(legalValueId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    
}
