package com.avocat.controller.privilege;

import com.avocat.common.AbstractRestTestController;
import com.avocat.persistence.entity.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.net.URI;
import java.util.UUID;

public class PrivilegeControllerTests extends AbstractRestTestController {

    @Test
    void shouldGetByIdPrivilegeAndWillReturnPrivilegeWithHttpStatus200() throws Exception {
        //given
        URI uri = new URI(HOST + "/avocat/v1/branch-office/6ef9a225-016f-40f0-ba0d-eae64a270a9e/privileges/2234d142-7a4a-4a8c-8431-b9a7a24c41de");
        //when
        HttpEntity<Privilege> request = new HttpEntity<>(headers);
        ResponseEntity<Privilege> result = this.testRestTemplate.exchange(uri, HttpMethod.GET, request, Privilege.class);
        //then
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(result.getBody().getId(), UUID.fromString("2234d142-7a4a-4a8c-8431-b9a7a24c41de"));
        Assertions.assertEquals(result.getBody().getName(), "ROLE_ADMIN");
    }
}
