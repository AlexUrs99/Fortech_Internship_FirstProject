package com.example.backend.user.repository;

import com.example.backend.user.model.ETrait;
import com.example.backend.user.model.Trait;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TraitRepositoryTest {

    @Autowired
    private TraitRepository traitRepository;

    @BeforeEach
    void setUp() {
        traitRepository.deleteAll();
    }

    @Test
    void findByName() {
        Trait focusedTrait = Trait.builder()
                .name(ETrait.FOCUSED)
                .build();

        Trait savedFocusedTrait = traitRepository.save(focusedTrait);

        Optional<Trait> foundTrait = traitRepository.findByName(ETrait.FOCUSED);

        assertTrue(foundTrait.isPresent() && foundTrait.get().equals(savedFocusedTrait));
    }
}