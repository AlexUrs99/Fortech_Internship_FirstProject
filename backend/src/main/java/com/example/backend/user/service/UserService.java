package com.example.backend.user.service;

import com.example.backend.user.exception.ApiRequestException;
import com.example.backend.user.mapper.UserMapper;
import com.example.backend.user.model.*;
import com.example.backend.user.model.dto.UserDTO;
import com.example.backend.user.repository.GenderRepository;
import com.example.backend.user.repository.TraitRepository;
import com.example.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TraitRepository traitRepository;
    private final GenderRepository genderRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Couldn't find user at id: " + id));
        return userMapper.toDTO(foundUser);
    }

    public UserDTO createUser(UserDTO userDTO) {

        User userToBeCreated = new User();

        if (userRepository.existsByUsername(userDTO.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        if (userRepository.existsByEmail(userDTO.getEmail()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");

        userToBeCreated.setUsername(userDTO.getUsername());
        userToBeCreated.setEmail(userDTO.getEmail());
        userToBeCreated.setFullName(userDTO.getFullName());


        if (!userDTO.getPassword().equals("")) {
            userToBeCreated.setPassword(userDTO.getPassword());
        }

        userToBeCreated.setTraits(mapTraits(userDTO.getTraits()));
        userToBeCreated.setGender(mapGender(userDTO.getGender()));

        User savedUser = userRepository.save(userToBeCreated);
        return userMapper.toDTO(savedUser);
    }

    private Set<Trait> mapTraits(Set<String> traits) {
        return traits.stream()
                .map(trait -> traitRepository.findByName(ETrait.valueOf(trait))
                        .orElseThrow(() -> new EntityNotFoundException("Couldn't find trait: " + trait.toUpperCase())))
                .collect(Collectors.toSet());
    }

    private Gender mapGender(String gender) {
        return genderRepository.findByName(EGender.valueOf(gender))
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find gender: " + gender.toUpperCase()));
    }

    private void verifyDataUnique(User actUser, UserDTO user) {
        if (!actUser.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username " + user.getUsername() + " already exists");
        }
        if (!actUser.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email " + user.getEmail() + " already exists");
        }
    }

    public UserDTO editUser(Long id, UserDTO user) {
        User actUser = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        actUser.setTraits(mapTraits(user.getTraits()));
        actUser.setGender(mapGender(user.getGender()));

        verifyDataUnique(actUser, user);

        actUser.setEmail(user.getEmail());
        actUser.setUsername(user.getUsername());
        actUser.setFullName(user.getFullName());


        if (!user.getPassword().equals("")) {
            actUser.setPassword(user.getPassword());
        }
        return userMapper.toDTO(userRepository.save(actUser));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
