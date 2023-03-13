package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.Paper;
import com.avocat.persistence.repository.process.PaperRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/papers", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class PaperController {

    @Autowired
    private PaperRepository paperRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<Paper> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody Paper paper) {
        paper.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(paperRepository.save(paper));
    }

    @Modifying
    @PutMapping
    public ResponseEntity<Paper> update(@PathVariable("customerId") UUID customerId, @RequestBody Paper paper) {
        return ResponseEntity.ok().body(paperRepository.save(paper));
    }

    @DeleteMapping("/{paperId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("paperId") UUID paperId) {
        paperRepository.deleteById(paperId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Paper>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(paperRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{paperId}")
    public ResponseEntity<Paper> findById(@PathVariable("paperId") UUID paperId,
                                               @PathVariable("customerId") UUID customerId) {
        var result = paperRepository.findByIdAndCustomerId(paperId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
