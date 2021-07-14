package com.example.backend.user.controller;

import com.example.backend.user.model.dto.UserDTO;
import com.example.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.backend.UrlMapping.ENTITY;
import static com.example.backend.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(ENTITY)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping(ENTITY)
    public UserDTO editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {
        return userService.editUser(id, userDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }


}
