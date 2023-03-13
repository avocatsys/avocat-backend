package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.ProceduralPhase;
import com.avocat.persistence.repository.process.ProceduralPhaseRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/procedural-phases", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ProceduralPhaseController {

    @Autowired
    private ProceduralPhaseRepository proceduralPhaseRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<ProceduralPhase> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody ProceduralPhase proceduralPhase) {
        proceduralPhase.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(proceduralPhaseRepository.save(proceduralPhase));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<ProceduralPhase> update(@PathVariable("customerId") UUID customerId, @RequestBody ProceduralPhase proceduralPhase) {
        return ResponseEntity.ok().body(proceduralPhaseRepository.save(proceduralPhase));
    }

    @DeleteMapping("/{proceduralPhaseId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("proceduralPhaseId") UUID proceduralPhaseId) {
        proceduralPhaseRepository.deleteById(proceduralPhaseId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ProceduralPhase>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(proceduralPhaseRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{proceduralPhaseId}")
    public ResponseEntity<ProceduralPhase> findById(@PathVariable("proceduralPhaseId") UUID proceduralPhaseId,
                                          @PathVariable("customerId") UUID customerId) {
        var result = proceduralPhaseRepository.findByIdAndCustomerId(proceduralPhaseId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
