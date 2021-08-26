package com.ru.microservice.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@DisplayName("Unit-level testing for RestService")
@ExtendWith(MockitoExtension.class)
@Slf4j
class RestServiceUnitTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestService restService;

    private String testMessage;

    private static final String TEST_SERVICE_URL = "http://ui-rest-service/api/messages";

    @BeforeEach
    void setUp() {
        testMessage = "1L, null, moscow, ru, 123456789L, null";
    }

    @DisplayName("Should return mock data")
    @Test
    void shouldReturnMockData() {
        ResponseEntity<String> response = new ResponseEntity<>(testMessage, HttpStatus.OK);

        restTemplate.getForObject(TEST_SERVICE_URL, String.class);

        log.info(response.getStatusCode() + " " + response.getBody());

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @DisplayName("Should return mock messages")
    @Test
    void shouldReturnMockMessages() {
        ResponseEntity<String> responseEntity = new ResponseEntity<>(testMessage, HttpStatus.OK);
        when(restTemplate.getForObject(
                Mockito.anyString(),
                Mockito.eq(String.class)))
                .thenReturn(String.valueOf(responseEntity));

        String message = restService.getSortedMessages();

        log.info("----------------------------------------------");
        log.info(String.valueOf(responseEntity.getStatusCode()));
        log.info(String.valueOf(responseEntity.getBody()));
        log.info(String.valueOf(testMessage));
        log.info(String.valueOf(message));
        log.info("----------------------------------------------");

        assertEquals(message, responseEntity.toString());
    }

    @DisplayName("Should return fallback constant")
    @Test
    void shouldReturnFallbackConstant() {
        String fallback = "Сервис временно не доступен. \n\nПопробуйте обновить страницу или зайти позднее.";

        assertEquals(fallback, restService.failed());
    }

    @DisplayName("Should not return fallback constant")
    @Test
    void shouldNotReturnFallbackConstant() {
        String fallback = "Сервис временно не доступен.";

        assertNotEquals(fallback, restService.failed());
    }
}