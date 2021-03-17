package com.ru.microservice.service;

import com.ru.microservice.model.Role;
import com.ru.microservice.model.User;
import com.ru.microservice.repository.RoleRepository;
import com.ru.microservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }
}