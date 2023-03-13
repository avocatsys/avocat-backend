package com.avocat.controller.process;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/v1/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AreaController {


/*
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE')")
    @PostMapping
    public ResponseEntity<Area> create(@PathVariable(value = "customerId", required = true) UUID customerId, @RequestBody Area area) {
        area.setCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(processGenericService.create(area));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE')")
    @PutMapping
    public ResponseEntity<Group> update(@ @PathVariable("customerId") UUID customerId, RequestBody Group group) {
        return ResponseEntity.ok().body(groupService.update(group));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_WRITE')")
    @DeleteMapping("/{groupId}")
    public ResponseEntity delete(@PathVariable("customerId") UUID customerId, PathVariable("id") UUID groupId)

    {
        groupService.delete(groupId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_READ')")
    @GetMapping
    public ResponseEntity<Page<Group>> findAll(
            @PathVariable("customerId") UUID customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findAll(branchOfficeId, pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER') or hasAuthority('LAWYER_READ')")
    @GetMapping("/{groupId}")
    public ResponseEntity<Group> findById(@PathVariable("id") UUID groupId,
                                          @PathVariable("customerId") UUID customerId) {
        return ResponseEntity.status(HttpStatus.OK).body(groupService.findById(groupId));
    }
*/}