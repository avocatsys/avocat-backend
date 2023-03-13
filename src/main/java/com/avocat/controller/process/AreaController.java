package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.Area;
import com.avocat.persistence.repository.process.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE', 'LAWYER_READ')")
@RequestMapping(path = "/v1/customer/{customerId}/areas", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AreaController {

    @Autowired
    private AreaRepository areaRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<Area> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody Area area) {
        area.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(areaRepository.save(area));
    }

    @PutMapping
    public ResponseEntity<Area> update(@PathVariable("customerId") UUID customerId, @RequestBody Area area) {
        area.setCustomerId(customerId);
        return ResponseEntity.ok().body(areaRepository.save(area));
    }

    @DeleteMapping("/{areaId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("areaId") UUID areaId) {
        areaRepository.deleteById(areaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Area>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(areaRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{areaId}")
    public ResponseEntity<Area> findById(@PathVariable("areaId") UUID areaId,
                                         @PathVariable("customerId") UUID customerId) {
        var result = areaRepository.findByIdAndCustomerId(areaId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}