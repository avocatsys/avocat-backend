package com.avocat.controller.process;

import com.avocat.common.AbstractMockMvcController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProcessControllerTests extends AbstractMockMvcController {

    @Test
    void shouldCreateNewProcessThenWillReturn201() throws Exception {

        var json = getProcessJson();

        this.mockMvc
                .perform(
                        post("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/process")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8")
                                .header("Authorization", "Bearer " + defaultAccessToken)
                                .content(json)
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    private String getProcessJson() {
        return """
                    {
                    "processNumber":"%s",
                    "auxiliaryCode":"1111111",
                    "internalObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "closingObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "customerObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "financialObservation":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis. ornare laoreet quisque ultricies at auctor curae aliquam rhoncus feugiat ac ultricies porttitor mauris imperdiet cras, inceptos aenean vehicula eget in taciti orci non mauris molestie augue venenatis porta tempus. sollicitudin dictum venenatis quisque pulvinar conubia amet duis velit venenatis, nunc mollis varius ipsum tincidunt egestas mattis interdum sodales, turpis aliquet netus enim eu nisi nam dictumst. netus commodo est torquent metus aptent diam mauris cras lacinia, curae quis erat faucibus erat etiam mi nunc tempor, litora consectetur nibh dictumst integer magna molestie quisque.",
                    "detailObject":"Lorem ipsum consectetur vulputate orci lacus proin mollis, urna rutrum dictumst elementum nisi bibendum nostra neque, tellus felis justo lorem maecenas convallis.",
                    "dateEntry":"2001-01-01",
                    "distributionDate":"2011-12-01",
                    "principalId": "49fbb8cc-3c47-4177-8bd0-47062c22211c",
                    "contraryId": "199e0d62-e6bb-441f-a3ff-9dc44b5dfdf3",
                    "contractId": "0735fe7e-182a-4d51-ab10-fbbdf9d289e8",
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

    @Test
    void shouldGetListAllProcessOfCustomersThenWillReturn200() throws Exception {

        mockMvc.perform(get("/v1/customer/d5d7da4a-4520-446e-9a6a-aaf4b76f803f/process")
                        .header("Authorization", "Bearer " + defaultAccessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.empty").value(Boolean.FALSE));
    }
}
