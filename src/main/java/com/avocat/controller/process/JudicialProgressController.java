package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.JudicialProgress;
import com.avocat.persistence.repository.process.JudicialProgressRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/judicial-progress", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class JudicialProgressController {

    @Autowired
    private JudicialProgressRepository judicialProgressRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<JudicialProgress> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody JudicialProgress judicialProgress) {
        judicialProgress.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(judicialProgressRepository.save(judicialProgress));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<JudicialProgress> update(@PathVariable("customerId") UUID customerId, @RequestBody JudicialProgress judicialProgress) {
        return ResponseEntity.ok().body(judicialProgressRepository.save(judicialProgress));
    }

    @DeleteMapping("/{judicialProgressId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("judicialProgressId") UUID judicialProgressId) {
        judicialProgressRepository.deleteById(judicialProgressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<JudicialProgress>> findAll(
            @PathVariable("judicialProgressId") UUID judicialProgressId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(judicialProgressRepository.findAllByCustomerId(judicialProgressId, pageable));
    }

    @GetMapping("/{judicialProgressId}")
    public ResponseEntity<JudicialProgress> findById(@PathVariable("judicialProgressId") UUID judicialProgressId,
                                               @PathVariable("customerId") UUID customerId) {
        var result = judicialProgressRepository.findByIdAndCustomerId(judicialProgressId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
