package com.ru.microservice.controller;

import com.ru.microservice.model.User;
import com.ru.microservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.core.MediaType;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping(value = "/home", produces = MediaType.APPLICATION_JSON)
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User admin = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Добро пожаловать " + admin.getName() + " " + admin.getLastName() + " (" + admin.getEmail() + ")");
        modelAndView.addObject("adminMessage", "Контент доступен только для пользователей с ролью администратора");
        modelAndView.setViewName("admin/home");
        log.info("Login to admin profile {}", admin);
        return modelAndView;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON)
    public ModelAndView getAll(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        log.info("Get all users: {}", users);
        modelAndView.setViewName("admin/list-users");
        return modelAndView;
    }

    @GetMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON)
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        userService.deleteById(id);
        log.info("Delete user with id {}", id);
        modelAndView.setViewName("redirect:/admin/users");
        return modelAndView;
    }
}