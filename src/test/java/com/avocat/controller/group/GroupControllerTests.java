package com.avocat.controller.group;

import com.avocat.persistence.entity.BranchOffice;
import com.avocat.persistence.entity.Customer;
import com.avocat.persistence.entity.Group;
import com.avocat.persistence.repository.BranchOfficeRepository;
import com.avocat.persistence.repository.GroupRepository;
import com.avocat.service.BranchOfficeService;
import com.avocat.service.GroupService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {GroupController.class, GroupService.class, BranchOfficeService.class})
public class GroupControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    private BranchOfficeRepository branchOfficeRepository;

    @Test
    @WithMockUser
    void givenNewGroup_whenGroupSave_thenNewGroupCreated() throws Exception {

        //given: providing initial input data
        var inputJson = getGroupJson();
        var groupExpected = Group.create(UUID.randomUUID(), "Group Test Integration", UUID.randomUUID());
        var branchOfficeExpected = new BranchOffice.Builder("67597486000168", "testeintegration@owemail.com", "OwTest Integration", "OwTest Integration", null).build();

        //when: executing class method to be tested
        when(groupRepository.save(any(Group.class))).thenReturn(groupExpected);
        when(branchOfficeRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(branchOfficeExpected));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/branch-office/8cb8dd3f-43b9-4ede-8c71-56ec064baced/groups")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(inputJson))
                //then: verifying and checking asserts
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Group Test Integration"))
                .andDo(print());
    }

    private String getGroupJson() {
        return """
                {                    
                    "name" : "Group Test Integration"
                }
                """;
    }
}
