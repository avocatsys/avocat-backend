package com.avocat.controller.process;

import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.process.Action;
import com.avocat.persistence.repository.process.ActionRepository;
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
@RequestMapping(path = "/v1/customer/{customerId}/actions", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ActionController {
    
    @Autowired
    private ActionRepository actionRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<Action> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody Action action) {
        action.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(actionRepository.save(action));
    }

    @PutMapping
    public ResponseEntity<Action> update(@PathVariable("customerId") UUID customerId, @RequestBody Action action) {
        return ResponseEntity.ok().body(actionRepository.save(action));
    }

    @DeleteMapping("/{actionId}")
    public ResponseEntity<Void> delete(@PathVariable("customerId") UUID customerId, @PathVariable("actionId") UUID actionId) {
        actionRepository.deleteById(actionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Action>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(actionRepository.findAllByCustomerId(customerId, pageable));
    }

    @GetMapping("/{actionId}")
    public ResponseEntity<Action> findById(@PathVariable("actionId") UUID actionId,
                                         @PathVariable("customerId") UUID customerId) {
        var result = actionRepository.findByIdAndCustomerId(actionId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
