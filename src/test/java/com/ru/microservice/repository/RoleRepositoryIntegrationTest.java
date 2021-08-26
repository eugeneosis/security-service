package com.ru.microservice.repository;

import com.ru.microservice.model.Role;
import com.ru.microservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Integration-level testing for UserRepository")
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@Slf4j
class RoleRepositoryIntegrationTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    private static final long TEST_USER_ID = 1;
    private static final long TEST_ROLE_ID = 2;

    @BeforeAll
    void setUp() {
        User testUser = new User(TEST_USER_ID, "testUser", "lastName", "testuser@mail.com",
                "usertest123", "usertest123", true,
                new Date(12345678L), Collections.singleton(new Role(TEST_ROLE_ID, "user")));
        savedUser = userRepository.save(testUser);
    }

    @DisplayName("Should find user by role")
    @Test
    void shouldFindByRole() {
        Role role = roleRepository.findByRole("user");

        log.info("------------------------------------------------------");
        log.info("1 :  " + role.getRole());
        log.info("2 :  " + savedUser.getRoles());
        log.info("------------------------------------------------------");

        assertThat(savedUser.getId()).isEqualTo(role.getId());
        assertThat(role.getRole()).isEqualTo("user");
    }

    @DisplayName("Should return list of roles")
    @Test
    void shouldReturnListOfRoles() {
        List<Long> listOfRoles = roleRepository.findAll().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        List<Long> checkUserRoleId = savedUser.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        log.info("------------------------------------------------------");
        log.info("0 :  " + listOfRoles);
        log.info("1 :  " + checkUserRoleId);
        log.info("------------------------------------------------------");

        assertEquals(listOfRoles, checkUserRoleId);
    }

    @DisplayName("Should find user role")
    @Test
    void shouldCompareUserAndRole() {
        List<String> findRoles = roleRepository.findAll().stream()
                .map(Role::getRole)
                .collect(Collectors.toList());

        List<String> listRoles = savedUser.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toList());

        log.info("------------------------------------------------------");
        log.info(String.valueOf(findRoles));
        log.info(String.valueOf(listRoles));
        log.info("------------------------------------------------------");

        assertThat(findRoles).isEqualTo(listRoles);
    }
}