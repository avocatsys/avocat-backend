package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.Rite;
import com.avocat.persistence.repository.process.RiteRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/rites", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class RiteController {

    @Autowired
    private RiteRepository riteRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<Rite> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody Rite rite) {
        rite.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(riteRepository.save(rite));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<Rite> update(@PathVariable("customerId") UUID customerId, @RequestBody Rite rite) {
        return ResponseEntity.ok().body(riteRepository.save(rite));
    }

    @DeleteMapping("/{riteId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("riteId") UUID riteId) {
        riteRepository.deleteById(riteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Rite>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(riteRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{riteId}")
    public ResponseEntity<Rite> findById(@PathVariable("riteId") UUID riteId,
                                                    @PathVariable("customerId") UUID customerId) {
        var result = riteRepository.findByIdAndCustomerId(riteId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
