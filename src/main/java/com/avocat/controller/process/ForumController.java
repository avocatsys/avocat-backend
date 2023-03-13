package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.Forum;
import com.avocat.persistence.repository.process.ForumRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/forums", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ForumController {

    @Autowired
    private ForumRepository forumRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<Forum> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody Forum forum) {
        forum.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(forumRepository.save(forum));
    }

    @PutMapping
    public ResponseEntity<Forum> update(@PathVariable("customerId") UUID customerId, @RequestBody Forum forum) {
        return ResponseEntity.ok().body(forumRepository.save(forum));
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("forumId") UUID forumId) {
        forumRepository.deleteById(forumId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Forum>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(forumRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{forumId}")
    public ResponseEntity<Forum> findById(@PathVariable("forumId") UUID forumId,
                                           @PathVariable("customerId") UUID customerId) {
        var result = forumRepository.findByIdAndCustomerId(forumId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
