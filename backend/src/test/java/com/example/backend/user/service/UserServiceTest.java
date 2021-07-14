package com.example.backend.user.service;

import com.example.backend.user.mapper.UserMapper;
import com.example.backend.user.model.*;
import com.example.backend.user.model.dto.UserDTO;
import com.example.backend.user.repository.GenderRepository;
import com.example.backend.user.repository.TraitRepository;
import com.example.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.backend.TestCreationFactory.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TraitRepository traitRepository;

    @Mock
    private GenderRepository genderRepository;

    @Mock
    private UserMapper userMapper;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, traitRepository, genderRepository, userMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }

    @Test
    void getAllUsers() {
        List<User> users = listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        //should mock mapper but I simply compare sizes for this

        List<UserDTO> fetchedUsers = userService.getAllUsers();

        Assertions.assertEquals(users.size(), fetchedUsers.size());
    }

    @Test
    void getUserById() {
        User user = User.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .build();

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        when(userMapper.fromDTO(userDTO)).thenReturn(user);

        UserDTO fetchedUser = userService.getUserById(user.getId());

        assertEquals(fetchedUser.getEmail(), user.getEmail());
        assertEquals(fetchedUser.getUsername(), user.getUsername());
        assertEquals(fetchedUser.getPassword(), user.getPassword());
        assertEquals(fetchedUser.getFullName(), user.getFullName());
        assertEquals(fetchedUser.getTraits(), user.getTraits());
        assertEquals(fetchedUser.getGender(), user.getGender());
    }

    @Test
    void createUser() {
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .email(randomString())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender("MALE")
                .build();

        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .fullName(userDTO.getFullName())
                .password(userDTO.getPassword())
                .traits(userDTO.getTraits()
                        .stream().map(trait -> Trait.builder().name(ETrait.valueOf(trait)).build())
                        .collect(Collectors.toSet()))
                .gender(Gender.builder().name(EGender.MALE).build())
                .build();

        when(userMapper.fromDTO(userDTO)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        when(userRepository.save(any())).thenReturn(user);
        when(traitRepository.findByName(any())).thenReturn(Optional.of(Trait.builder()
                .name(ETrait.FOCUSED).build()));
        when(genderRepository.findByName(any())).thenReturn(Optional.of(Gender.builder()
                .name(EGender.MALE).build()));


        UserDTO createdUser = userService.createUser(userDTO);

        assertEquals(createdUser, userDTO);
    }

    @Test
    void createUserExpectResponseStatusExceptionSameUsernameTest() {
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .email(randomString())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender("MALE")
                .build();

        given(userRepository.existsByUsername(userDTO.getUsername())).willReturn(true);

        assertThatThrownBy(() -> userService.createUser(userDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username already exists!");
    }

    @Test
    void createUserExpectResponseStatusExceptionSameEmailTest() {
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .email(randomString())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender("MALE")
                .build();

        given(userRepository.existsByEmail(userDTO.getEmail())).willReturn(true);

        assertThatThrownBy(() -> userService.createUser(userDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Email already exists!");
    }

    @Test
    void editUser() {
        UserDTO userDTO = UserDTO.builder()
                .id(randomLong())
                .email(randomString())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender("MALE")
                .build();

        User user = User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .fullName(userDTO.getFullName())
                .password(userDTO.getPassword())
                .traits(userDTO.getTraits()
                        .stream().map(trait -> Trait.builder().name(ETrait.valueOf(trait)).build())
                        .collect(Collectors.toSet()))
                .gender(Gender.builder().name(EGender.MALE).build())
                .build();

        when(userMapper.fromDTO(userDTO)).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(userDTO.getId())).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userMapper.toDTO(user)).thenReturn(userDTO);
        when(userRepository.save(any())).thenReturn(user);
        when(traitRepository.findByName(any())).thenReturn(Optional.of(Trait.builder()
                .name(ETrait.FOCUSED).build()));
        when(genderRepository.findByName(any())).thenReturn(Optional.of(Gender.builder()
                .name(EGender.MALE).build()));

        UserDTO editedUser = userService.editUser(user.getId(), userDTO);

        Assertions.assertEquals(editedUser, userDTO);
    }

    @Test
    void editUserExpectResponseStatusExceptionSameUsernameTest() {
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
                .username(randomString())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender(EGender.MALE.name())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(traitRepository.findByName(any())).thenReturn(Optional.of(Trait.builder()
                .name(ETrait.FOCUSED)
                .build()));
        when(genderRepository.findByName(any())).thenReturn(Optional.of(Gender.builder()
                .name(EGender.MALE)
                .build()));

        given(userRepository.existsByUsername(userDTO.getUsername())).willReturn(true);


        assertThatThrownBy(() -> userService.editUser(user.getId(), userDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Username " + userDTO.getUsername() + " already exists");
    }

    @Test
    void editUserExpectResponseStatusExceptionSameEmailTest() {
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
                .email(randomEmail())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .password(user.getPassword())
                .traits(new HashSet<>(asList("FOCUSED")))
                .gender(EGender.MALE.name())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(traitRepository.findByName(any())).thenReturn(Optional.of(Trait.builder()
                .name(ETrait.FOCUSED)
                .build()));
        when(genderRepository.findByName(any())).thenReturn(Optional.of(Gender.builder()
                .name(EGender.MALE)
                .build()));

        given(userRepository.existsByEmail(userDTO.getEmail())).willReturn(true);


        assertThatThrownBy(() -> userService.editUser(user.getId(), userDTO))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Email " + userDTO.getEmail() + " already exists");
    }

    @Test
    void deleteUser() {
        Trait focusedTrait = Trait.builder().name(ETrait.FOCUSED).build();
        User user = User.builder()
                .id(randomLong())
                .email(randomString())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .traits(new HashSet<>(asList(focusedTrait)))
                .gender(Gender.builder().name(EGender.MALE).build())
                .build();

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).deleteById(user.getId());

    }
}