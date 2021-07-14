package com.example.backend.user.mapper;

import com.example.backend.user.model.*;
import com.example.backend.user.model.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static com.example.backend.TestCreationFactory.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void toDTO() {
        Trait focusedTrait = Trait.builder().name(ETrait.FOCUSED).build();
        Gender maleGender = Gender.builder().name(EGender.MALE).build();

        User user = User.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList(focusedTrait)))
                .gender(maleGender)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender(EGender.MALE.name())
                .build();

        UserDTO resultedValue = userMapper.toDTO(user);

        assertEquals(userDTO, resultedValue);
    }

    @Test
    void fromDTO() {
        Trait focusedTrait = Trait.builder().name(ETrait.FOCUSED).build();
        Gender maleGender = Gender.builder().name(EGender.MALE).build();

        User user = User.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList(focusedTrait)))
                .gender(maleGender)
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender(EGender.MALE.name())
                .build();

        User resultedValue = userMapper.fromDTO(userDTO);

        assertEquals(user, resultedValue);
    }

    @Test
    void mapTraitsToStrings() {
        Trait focusedTrait = Trait.builder().name(ETrait.FOCUSED).build();
        Trait caringTrait = Trait.builder().name(ETrait.CARING).build();
        Trait courageousTrait = Trait.builder().name(ETrait.COURAGEOUS).build();

        HashSet<Trait> traits = new HashSet<>(asList(focusedTrait, caringTrait, courageousTrait));

        Set<String> resultedValue = UserMapper.mapTraitsToStrings(traits);

        HashSet<String> expectedValue = new HashSet<>(asList("FOCUSED", "CARING", "COURAGEOUS"));

        System.out.println(expectedValue);
        System.out.println(resultedValue);

        assertEquals(expectedValue, resultedValue);
    }

    @Test
    void mapStringToTraits() {
        Trait focusedTrait = Trait.builder().name(ETrait.FOCUSED).build();
        Trait caringTrait = Trait.builder().name(ETrait.CARING).build();
        Trait courageousTrait = Trait.builder().name(ETrait.COURAGEOUS).build();

        HashSet<String> setOfStrings = new HashSet<>(asList("FOCUSED", "CARING", "COURAGEOUS"));

        Set<Trait> resultedValue = UserMapper.mapStringToTraits(setOfStrings);

        HashSet<Trait> expectedValue = new HashSet<>(asList(focusedTrait, caringTrait, courageousTrait));

        assertEquals(expectedValue, resultedValue);
    }

    @Test
    void mapGenderToString() {
        Gender maleGender = Gender.builder().name(EGender.MALE).build();

        String expectedMaleAsString = UserMapper.mapGenderToString(maleGender);

        assertThat(expectedMaleAsString.equals(maleGender.getName().toString()));
    }

    @Test
    void mapStringToGender() {
        String maleAsString = "MALE";

        Gender resultedValue = UserMapper.mapStringToGender(maleAsString);

        Gender expectedValue = Gender.builder().name(EGender.MALE).build();

        assertThat(expectedValue.equals(resultedValue));
    }
}