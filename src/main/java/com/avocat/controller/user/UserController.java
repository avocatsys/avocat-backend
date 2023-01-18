package com.avocat.controller.user;

import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.persistence.entity.UserApp;
import com.avocat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserAppDto> create(@RequestBody UserApp user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
    }

    @PutMapping("/{userId}/branch-offices/{branchOfficeId}")
    public ResponseEntity<UserAppDto> update(
            @PathVariable("userId") UUID userId,
            @PathVariable("branchOfficeId") UUID branchOfficeId,
            @RequestBody UserApp user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.update(userId, branchOfficeId, user));
    }
}
