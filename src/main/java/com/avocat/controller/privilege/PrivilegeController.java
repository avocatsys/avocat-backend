package com.avocat.controller.privilege;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@PreAuthorize("hasRole('ROLE_ADMIN') or hasAuthority('PRIVILEGE_WRITE')")
@RequestMapping(path = "/v1/privileges", produces = MediaType.APPLICATION_JSON_VALUE)
public class PrivilegeController {

    //@PreAuthorize("hasRole('{ ROLE_ADMIN, ROLE_USER }')")
    @GetMapping
    public String findAll() {//todo trocar para pageble
        return "xx";
    }
}
