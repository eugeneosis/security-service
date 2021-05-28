package com.ru.microservice.controller;

import com.ru.microservice.model.User;
import com.ru.microservice.service.RestService;
import com.ru.microservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.core.MediaType;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final RestService restService;

    @GetMapping(value = "/main", produces = MediaType.APPLICATION_JSON)
    public ModelAndView userPage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Добро пожаловать " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("userMessage", "Контент доступен только для пользователей");
        modelAndView.setViewName("users/main");
        log.info("Login to user profile {}", user);
        return modelAndView;
    }

    @GetMapping(value = "/rest/users", produces = MediaType.APPLICATION_JSON)
    public String usersRest() {
        log.info("Fetching users data from UI-REST-SERVICE through Rest Service");
        return restService.getAllUsers();
    }

    @GetMapping(value = "/rest/messages", produces = MediaType.APPLICATION_JSON)
    public String messagesRest() {
        log.info("Fetching messages data from UI-REST-SERVICE through Rest Service");
        return restService.getAllMessages();
    }
}