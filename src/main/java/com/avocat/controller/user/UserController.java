package com.avocat.controller.user;

import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.persistence.entity.Group;
import com.avocat.persistence.entity.UserApp;
import com.avocat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/v1/branch-office/{branchOfficeId}/users")
    public ResponseEntity<UserAppDto> create(
            @PathVariable("branchOfficeId") UUID branchOfficeId,
            @RequestBody @Valid UserApp user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(branchOfficeId, user));
    }

    @PutMapping("/v1/branch-office/{branchOfficeId}/users")
    public ResponseEntity<UserAppDto> update(
            @PathVariable("userId") UUID userId,
            @PathVariable("branchOfficeId") UUID branchOfficeId,
            @RequestBody UserApp user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, branchOfficeId, user));
    }

    @DeleteMapping("/v1/branch-office/{branchOfficeId}/users/{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/v1/customer/{customerId}/users")
    public ResponseEntity<Page<UserAppDto>> findAll(
            @PathVariable("customerId") UUID branchOfficeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(branchOfficeId, pageable));
    }

    @GetMapping("/v1/branch-office/{branchOfficeId}/users/{id}")
    public ResponseEntity<UserAppDto> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }
}
