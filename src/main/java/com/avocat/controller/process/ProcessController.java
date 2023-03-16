package com.avocat.controller.process;

import com.avocat.controller.process.dto.ProcessDto;
import com.avocat.persistence.entity.process.Process;
import com.avocat.service.ProcessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/v1/customer/{customerId}/process", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class ProcessController {

    private final ProcessService processService;

    public ProcessController(ProcessService processService) {
        this.processService = processService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE')")
    @PostMapping
    public ResponseEntity<Process> create(
            @PathVariable(value = "customerId") UUID customerId,
            @RequestBody ProcessDto processDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(processService.create(processDto, customerId));
    }
}
