package com.example.backend.user.repository;

import com.example.backend.user.model.EGender;
import com.example.backend.user.model.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GenderRepositoryTest {

    @Autowired
    private GenderRepository genderRepository;

    @BeforeEach
    void setUp() {
        genderRepository.deleteAll();
    }

    @Test
    void findByName() {
        Gender maleGender = Gender.builder()
                .name(EGender.MALE)
                .build();

        Gender savedMaleGender = genderRepository.save(maleGender);

        Optional<Gender> foundGender = genderRepository.findByName(EGender.MALE);

        assertTrue(foundGender.isPresent() && foundGender.get().equals(savedMaleGender));
    }
}