package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.JudicialDistrict;
import com.avocat.persistence.repository.process.JudicialDistrictRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/judicial-districts", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class JudicialDistrictController {

    @Autowired
    private JudicialDistrictRepository judicialDistrictRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<JudicialDistrict> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody JudicialDistrict judicialDistrict) {
        judicialDistrict.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(judicialDistrictRepository.save(judicialDistrict));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<JudicialDistrict> update(@PathVariable("customerId") UUID customerId, @RequestBody JudicialDistrict judicialDistrict) {
        return ResponseEntity.ok().body(judicialDistrictRepository.save(judicialDistrict));
    }

    @DeleteMapping("/{judicialDistrictId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("judicialDistrictId") UUID judicialDistrictId) {
        judicialDistrictRepository.deleteById(judicialDistrictId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<JudicialDistrict>> findAll(
            @PathVariable("judicialDistrictId") UUID judicialDistrictId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(judicialDistrictRepository.findAllByCustomerId(judicialDistrictId, pageable));
    }

    @GetMapping("/{judicialDistrictId}")
    public ResponseEntity<JudicialDistrict> findById(@PathVariable("judicialDistrictId") UUID judicialDistrictId,
                                          @PathVariable("customerId") UUID customerId) {
        var result = judicialDistrictRepository.findByIdAndCustomerId(judicialDistrictId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}