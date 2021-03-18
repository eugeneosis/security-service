package com.ru.microservice.controller;

import com.ru.microservice.model.User;
import com.ru.microservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@AllArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping(value = "/admin/home")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Добро пожаловать " + "/" + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Контент доступен только для пользователей с ролью администратора");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

    @GetMapping(value = "/admin/home/users")
    public List<User> getAll() {
        return userService.getAll();
    }
}
