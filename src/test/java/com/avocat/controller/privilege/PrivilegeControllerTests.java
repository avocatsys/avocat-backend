package com.avocat.controller.privilege;

import com.avocat.controller.AbstractBaseController;
import com.avocat.persistence.entity.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public class PrivilegeControllerTests extends AbstractBaseController {

    @Test
    void shouldGetAllPrivilegesWithHttpStatus200() throws Exception {
        //given
        URI uri = new URI(HOST + "/avocat/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/privileges");
        //when
        HttpEntity<List<Privilege>> request = new HttpEntity<>(headers);
        ResponseEntity<List<Privilege>> result = this.testRestTemplate.exchange(uri, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
        //then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void shouldGetByIdPrivilegeAndWillReturnPrivilegeROLE_ADMINwithHttpStatus200() throws Exception {
        //given
        URI uri = new URI(HOST + "/avocat/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/privileges/2234d142-7a4a-4a8c-8431-b9a7a24c41de");
        //when
        HttpEntity<Privilege> request = new HttpEntity<>(headers);
        ResponseEntity<Privilege> result = this.testRestTemplate.exchange(uri, HttpMethod.GET, request, Privilege.class);
        //then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(result.getBody().getId(), UUID.fromString("2234d142-7a4a-4a8c-8431-b9a7a24c41de"));
        Assertions.assertEquals(result.getBody().getName(), "ROLE_ADMIN");
    }

    @Test
    void shouldCreateNewPrivilegeAndReturnHttpStatus201() throws Exception {

        URI uri = new URI(HOST + "/avocat/v1/branch-office/65344a5e-81ce-4eb3-b16b-955d26b73ede/privileges");

        var privilegeName = "Test" + UUID.randomUUID().toString().substring(0, 5);
        var privilege = Privilege.create(privilegeName);

        HttpEntity<Privilege> request = new HttpEntity<>(privilege, headers);

        ResponseEntity<Privilege> result = this.testRestTemplate.exchange(uri, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});

        Assertions.assertEquals(result.getStatusCode(), HttpStatus.CREATED);
        Assertions.assertNotNull(result.getBody().getId());
        Assertions.assertEquals(result.getBody().getName(), privilegeName);
    }
}
