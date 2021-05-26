package com.ru.microservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class RestService {

    private final RestTemplate template;

    @HystrixCommand(fallbackMethod = "failed")
    public String getAllUsers() {
        String response = template.getForObject("http://ui-rest-service/users", String.class);
        log.info(response);
        return response;
    }

    @HystrixCommand(fallbackMethod = "failed")
    public String getAllMessages() {
        String response = template.getForObject("http://ui-rest-service/messages", String.class);
        log.info(response);
        return response;
    }

    public String failed() {
        String error = "Сервис временно не доступен. Попробуйте обновить страницу или зайти позднее.";
        log.error(error);
        return error;
    }
}
