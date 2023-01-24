package com.avocat.controller.privilege;

import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.repository.PrivilegeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(controllers = PrivilegeController.class)
public class PrivilegeControllerTests {

    @Mock
    private PrivilegeController privilegeController;

    //@InjectMocks
    //private GenericService genericService;

    @Mock
    private PrivilegeRepository privilegeRepository;

    //@Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(privilegeController).build();
        //genericService = mock(GenericService.class);
    }

    @Test
    @WithMockUser(username = "admin")
    void shouldReturnListOfPrivileges() throws Exception {


        var privilegeList = List.of(
                new Privilege("ROLE_ADMIN"),
                new Privilege("GROUP_WRITE"),
                new Privilege("ROLE_USER"));

        when(privilegeRepository.findAll()).thenReturn(privilegeList);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/privileges")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("utf-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(jsonPath("$.size()").value(privilegeList.size()))
                .andDo(print());
    }
}
