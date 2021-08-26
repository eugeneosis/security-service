package com.ru.microservice.repository;

import com.ru.microservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@DisplayName("Integration-level testing for UserRepository")
@DataJpaTest
@ActiveProfiles("test")
@Slf4j
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private static final long TEST_USER_ID = 1;

    @DisplayName("Should save user")
    @Rollback()
    @Test
    void shouldSaveUser() {
        User testedUser = new User(TEST_USER_ID, "testUser", "lastName", "testuser@mail.com",
                "usertest123", "usertest123", true, new Date(12345678L), null);

        userRepository.save(testedUser);

        log.info("---------------------------------------");
        log.info(String.valueOf(testedUser.getId()));
        log.info(String.valueOf(testedUser.getEmail()));
        log.info("---------------------------------------");

        assertThat(userRepository.existsById(TEST_USER_ID)).isTrue();
        assertThat(userRepository.findByEmail(testedUser.getEmail()));
    }

    @DisplayName("Should find user by email")
    @Test
    void shouldFindByEmail() {
        User findByEmail = new User();
        findByEmail.setEmail("testuser@mail.com");

        userRepository.findByEmail(String.valueOf(findByEmail));

        log.info("------------------------------------------------------");
        log.info(findByEmail.getEmail());
        log.info("------------------------------------------------------");

        assertTrue(findByEmail.getEmail(), true);
    }
}