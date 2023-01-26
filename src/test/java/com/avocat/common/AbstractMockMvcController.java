package com.avocat.common;

import com.avocat.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class AbstractMockMvcController extends TokenUtil {

    @Autowired
    protected MockMvc mockMvc;
}
