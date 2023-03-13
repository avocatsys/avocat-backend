package com.avocat.controller.process;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OWNER')")
public class JudicialProgressController {
}
