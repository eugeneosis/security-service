package com.ru.microservice.service;

import com.ru.microservice.model.Role;
import com.ru.microservice.model.User;
import com.ru.microservice.repository.RoleRepository;
import com.ru.microservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public List<User> getAll() { return userRepository.findAll(); }

    @Transactional
    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRegistered(new Date());
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    @Transactional
    public User setRole(User user) {
        if (user.getEmail().equals("admin@gmail.com")) {
            Role adminRole = roleRepository.findByRole("ADMIN");
            user.setRoles(new HashSet<>(Collections.singletonList(adminRole)));
        }
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) { userRepository.deleteById(id); }
}