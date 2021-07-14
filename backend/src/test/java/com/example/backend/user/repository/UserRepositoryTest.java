package com.example.backend.user.repository;

import com.example.backend.user.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.HashSet;

import static com.example.backend.TestCreationFactory.randomString;
import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void existsByUsername() {

        User userToBeSaved = User.builder()
                .username(randomString())
                .fullName(randomString())
                .email(randomString())
                .password(randomString())
                .build();

        User saved = userRepository.save(userToBeSaved);

        boolean isSaved = userRepository.existsByUsername(userToBeSaved.getUsername());

        assertThat(isSaved).isTrue();
    }

    @Test
    void existsByEmail() {

        User userToBeSaved = User.builder()
                .username(randomString())
                .fullName(randomString())
                .email(randomString())
                .password(randomString())
                .build();

        User saved = userRepository.save(userToBeSaved);

        boolean isSaved = userRepository.existsByEmail(userToBeSaved.getEmail());

        assertThat(isSaved).isTrue();
    }
}