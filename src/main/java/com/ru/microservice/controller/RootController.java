package com.ru.microservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping(value = "/")
    public String root() {
        return "index";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }
}