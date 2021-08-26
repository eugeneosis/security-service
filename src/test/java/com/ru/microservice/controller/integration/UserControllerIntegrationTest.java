package com.ru.microservice.controller.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Integration-level testing for UserController")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static final String TEST_HOST_URL = "http://localhost:";
    private static final String TEST_USERS_URL = "/users/main";

    @DisplayName("Should return Redirect status")
    @Test
    void shouldReturnRedirectToLogin() {
        ResponseEntity<String> response = restTemplate.getForEntity(TEST_HOST_URL + port + TEST_USERS_URL, String.class);

        log.info("------------------------------------------------");
        log.info(String.valueOf(response.getHeaders()));
        log.info(String.valueOf(response.getStatusCode()));
        log.info(String.valueOf(response.getBody()));
        log.info("------------------------------------------------");

        assertThat(response.getStatusCode().equals(HttpStatus.FOUND));
    }
}
