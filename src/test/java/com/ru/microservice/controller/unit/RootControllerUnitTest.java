package com.ru.microservice.controller.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Unit-level testing for RootController")
@WebMvcTest
@WithMockUser("john")
class RootControllerUnitTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    public static final String TEST_INDEX_PAGE_URL = "/";
    public static final String TEST_LOGIN_PAGE_URL = "/login/";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void root() throws Exception {
        mockMvc.perform(get(TEST_INDEX_PAGE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get(TEST_LOGIN_PAGE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }
}