package com.ru.microservice.controller.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Unit-level testing for UserController")
@WebMvcTest
@WithMockUser(username = "john@somemail.com", password = "johnpassword", roles = "USER")
class UserControllerUnitTest extends AbstractController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String TEST_USERS_REST_MESSAGES_URL = "/users/rest/messages";
    private static final String TEST_USERS_TABLE_PAGE_URL = "/users/table";
    private static final String TEST_USERS_CHART_PAGE_URL = "/users/charts";
    private static final String TEST_USERS_PAGE_URL = "/users/main";

    @BeforeEach
    public void setup() { mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }

    @DisplayName("Should return user page")
    @Test
    void shouldReturnUserPage() throws Exception {
        mockMvc.perform(get(TEST_USERS_PAGE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("Should return data")
    @Test
    void shouldReturnData() throws Exception {
        mockMvc.perform(get(TEST_USERS_REST_MESSAGES_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("Should return table page")
    @Test
    void shouldReturnTablePage() throws Exception {
        mockMvc.perform(get(TEST_USERS_TABLE_PAGE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("Should return chart page")
    @Test
    void shouldReturnChartPage() throws Exception {
        mockMvc.perform(get(TEST_USERS_CHART_PAGE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}