package com.avocat.controller.group;

import com.avocat.controller.GenericControllerImpl;
import com.avocat.persistence.entity.Group;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class GroupController extends GenericControllerImpl<Group> {

}
