package com.avocat.controller.process;

import com.avocat.controller.process.dto.GetProcessDto;
import com.avocat.controller.process.dto.ProcessDto;
import com.avocat.service.ProcessService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<Void> create(
            @PathVariable(value = "customerId") UUID customerId,
            @RequestBody ProcessDto processDto) {

        processService.create(processDto, customerId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE', 'LAWYER_READ')")
    @GetMapping
    public ResponseEntity<Page<GetProcessDto>> findaAll(
            @PathVariable(value = "customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(processService.findAll(customerId, pageable));
    }
}
