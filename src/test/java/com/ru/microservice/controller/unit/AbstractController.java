package com.ru.microservice.controller.unit;

import com.ru.microservice.service.EmailSenderService;
import com.ru.microservice.service.RestService;
import com.ru.microservice.service.SecurityDetailsService;
import com.ru.microservice.service.UserService;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.servlet.ModelAndView;

abstract class AbstractController {

    @MockBean
    private SecurityDetailsService securityDetailsService;
    @MockBean
    private UserService userService;
    @MockBean
    private EmailSenderService emailSenderService;
    @MockBean
    private RestService restService;
    @Mock
    ModelAndView modelAndView;
}
