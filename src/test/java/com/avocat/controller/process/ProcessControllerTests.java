package com.avocat.controller.process;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProcessControllerTests extends AbstractMockMvcController {

    @Test
    void shouldCreateProcessThenWillReturn201() throws Exception {

        var json = getProcessJson();

        this.mockMvc
                .perform(
                        post("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/process")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .content(json)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    private String getProcessJson() {
        return """
                    {
                    "processNumber":"%s",
                    "auxiliaryCode":"",
                    "internalObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "closingObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "customerObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "financialObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "detailObject":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis.",
                    "dateEntry":"2001-01-01",
                    "distributionDate":"2011-12-01",
                    "principalId": "fffc0201-e0d6-463f-a004-8e76856a15fd",
                    "contraryId": "a0a5bc8c-bf51-4ea7-a360-8d205c291b98",
                    "contractId": "e8fa4fdb-f33b-4bc5-ab71-a6c2a5c7f4e0",
                    "area": {
                        "id":"6b51d71c-27f2-4d19-853a-b2fbfe88a9ef",
                        "name":"Trabalhista",
                        "active":true
                        },
                        "action": {
                        "id":"d3ebb26c-4c96-436e-923e-ece2319ef82a",
                        "name":"Consultivo",
                        "active":true
                    },
                    "judicialDistrict": {
                        "id":"f9cad642-e2ef-465a-ac74-8746cd6e5b28",
                        "name":"Comarca 1",
                        "active":true
                        },
                    "forum": {
                        "id":"89c9e097-68d7-44d9-99c6-8a9446ca1620",
                        "name":"Santo Amaro",
                        "active":true
                        },
                    "legalBranch": {
                        "id":"2f19be6d-d28c-40f9-9dac-73f637ba2156",
                        "name":"Vara 1",
                        "active":true
                        }
                    }
                """.formatted(
                UUID.randomUUID().toString().substring(0, 20));
    }
}
