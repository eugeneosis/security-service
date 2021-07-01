package com.ru.microservice.controller;

import com.ru.microservice.model.User;
import com.ru.microservice.service.EmailSenderService;
import com.ru.microservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

@RestController
@AllArgsConstructor
@Slf4j
public class ProcessingController {

    private final UserService userService;
    private final EmailSenderService emailSenderService;

    @GetMapping(value = "/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Пользователь с таким адресом электронной почты уже зарегистрирован!");
        } else if (!user.getPassword().equals(user.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.passwordConfirm", "Пароли не совпадают");
        }
        if (!bindingResult.hasErrors()) {
            userService.createUser(user);
            userService.setRole(user);
            log.info("Created new user: {}", user);
            modelAndView.addObject("successMessage", "Вы успешно зарегистрированы! \n\n Перейдите на страницу \n\nавторизации");
            emailSenderService.sendEmail(user.getEmail(), "allWeatherRussiaBot", user.getName() + ", спасибо за Ваш интерес к сервису. \n\nВы успешно зарегистрированы!");
            log.info("Sent email to {}", user.getEmail());
        }
        modelAndView.setViewName("registration");
        return modelAndView;
    }
}