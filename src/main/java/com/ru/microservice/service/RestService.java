package com.ru.microservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class RestService {

    private final RestTemplate template;

    private static final String SERVICE_FALLBACK_CONSTANT = "Сервис временно не доступен. Попробуйте обновить страницу или зайти позднее.";

    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 50, multiplier = 2))
    public String getAllUsers() {
        String response = template.getForObject("http://ui-rest-service/users", String.class);
        log.info(response);
        return response;
    }

    @Retryable(maxAttempts = 5, value = RuntimeException.class, backoff = @Backoff(delay = 50, multiplier = 2))
    public String getAllMessages() {
        String response = template.getForObject("http://ui-rest-service/messages", String.class);
        log.info(response);
        return response;
    }

    @Recover
    public String failed() {
        log.error(SERVICE_FALLBACK_CONSTANT);
        return SERVICE_FALLBACK_CONSTANT;
    }
}