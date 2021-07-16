package com.example.backend.user.controller;

import com.example.backend.user.model.dto.UserDTO;
import com.example.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> allUsers = userService.getAllUsers();

        HttpHeaders header = new HttpHeaders();
        header.add("Description", "Fetched all the users.");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(allUsers);
    }

    @GetMapping(ENTITY)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.getUserById(id);

        HttpHeaders header = new HttpHeaders();
        header.add("Description", "Fetched user at id: " + id + ".");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(foundUser);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);

        HttpHeaders header = new HttpHeaders();
        header.add("Description", "Created a new user. See its body in the Body section.");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(createdUser);
    }

    @PutMapping(ENTITY)
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO, @PathVariable Long id) {

        UserDTO editedUser = userService.editUser(id, userDTO);

        HttpHeaders header = new HttpHeaders();
        header.add("Description", "Edited the user at id: " + id + ". See its new in the Body section.");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(editedUser);

    }

    @DeleteMapping(ENTITY)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        HttpHeaders header = new HttpHeaders();
        header.add("Description", "Deleted the user at id: " + id + ".");

        return new ResponseEntity<Void>(header, HttpStatus.OK);
    }
}
