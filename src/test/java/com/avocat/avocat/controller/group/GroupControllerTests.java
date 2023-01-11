package com.avocat.avocat.controller.group;

import com.avocat.avocat.controller.RestAuthenticationControllerBaseTest;
import com.avocat.persistence.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupControllerTests extends RestAuthenticationControllerBaseTest {

    @Test
    void givenNewGroup_whenNewGroupCreated_thenHttpStatusCreated_201() throws Exception {

        var newGroup = Group.create("Grupo " + UUID.randomUUID());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + AccessToken);

        HttpEntity<Group> request = new HttpEntity<>(newGroup, headers);

        URI uri = new URI(host + port + "/avocat/v1/groups");

        ResponseEntity<Group> result = restTemplate.exchange(uri, HttpMethod.POST, request, Group.class);

        assertEquals(result.getStatusCode(), HttpStatus.CREATED);
    }
}
