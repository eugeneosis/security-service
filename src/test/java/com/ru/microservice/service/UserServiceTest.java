package com.ru.microservice.service;

import com.ru.microservice.model.Role;
import com.ru.microservice.model.User;
import com.ru.microservice.repository.RoleRepository;
import com.ru.microservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Unit-level testing for UserService")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    private User testedUser;

    private static final long TEST_USER_ID = 1;
    private static final long TEST_ROLE_ID = 2;

    @BeforeEach
    void setUp() {
        testedUser = new User(TEST_USER_ID, "testUser", "lastName", "testuser@mail.com",
                bCryptPasswordEncoder.encode("usertest123"), "usertest123", true,
                new Date(), singleton(new Role(TEST_ROLE_ID, "user")));
    }

    @DisplayName("Should find user by email")
    @Test
    void shouldFindUserByEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(testedUser);

        User userByEmail = userService.findUserByEmail(testedUser.getEmail());

        assertEquals(userByEmail.getEmail(), testedUser.getEmail());
    }

    @DisplayName("Should get all users")
    @Test
    void shouldGetAll() {
        when(userRepository.findAll()).thenReturn(singletonList(testedUser));

        userService.getAll();

        List<User> userList = userService.getAll();

        assertEquals(userList, userService.getAll());
    }

    @DisplayName("Should create new user")
    @WithMockUser
    @Test
    void shouldCreateUser() {
        when(roleRepository.findByRole(any())).thenReturn(new Role(TEST_ROLE_ID, "USER"));
        when(userRepository.save(any())).thenReturn(testedUser);

        User user = userService.createUser(testedUser);

        Role userRole = roleRepository.findByRole("USER");

        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

        assertThat(user).isEqualTo(testedUser);
    }

    @DisplayName("Should delete user by id")
    @ParameterizedTest
    @CsvSource({"1", "2"})
    void shouldDeleteById(Long id) {
        userService.deleteById(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}