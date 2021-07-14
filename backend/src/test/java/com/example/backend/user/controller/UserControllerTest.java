package com.example.backend.user.controller;

import com.example.backend.BaseControllerTest;
import com.example.backend.TestCreationFactory;
import com.example.backend.user.model.dto.UserDTO;
import com.example.backend.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.backend.TestCreationFactory.*;
import static com.example.backend.UrlMapping.ENTITY;
import static com.example.backend.UrlMapping.USERS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController usersController;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        usersController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDTO> userDTOList = TestCreationFactory.listOf(UserDTO.class);
        when(userService.getAllUsers()).thenReturn(userDTOList);
        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userDTOList));
    }

    @Test
    void getUserById() throws Exception {
        long id = randomLong();
        UserDTO user = UserDTO.builder()
                .id(id)
                .email(randomEmail())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .build();
        when(userService.getUserById(id)).thenReturn(user);

        mockMvc.perform(get(USERS + "/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void createUser() throws Exception {
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .fullName(randomString())
                .password(randomString())
                .build();
        when(userService.createUser(user)).thenReturn(user);
        ResultActions result = performPostWithRequestBody(USERS, user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void editUser() throws Exception {
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .fullName(randomString())
                .build();
        when(userService.editUser(user.getId(), user)).thenReturn(user);
        ResultActions result = performPutRequestWithPathVariableAndRequestBody(USERS + ENTITY, user.getId().toString(),user);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void deleteUser() throws Exception {
        UserDTO user = UserDTO.builder()
                .id(randomLong())
                .email(randomEmail())
                .username(randomString())
                .password(randomString())
                .build();
        ResultActions result = performDeleteWithPathVariable(USERS + ENTITY, user.getId().toString());
        result.andExpect(status().isOk());

    }
}