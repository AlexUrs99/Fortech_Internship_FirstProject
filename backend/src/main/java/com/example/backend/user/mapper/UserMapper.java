package com.example.backend.user.mapper;

import com.example.backend.user.model.ERole;
import com.example.backend.user.model.Role;
import com.example.backend.user.model.User;
import com.example.backend.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "RoleToString")
    UserDTO toDTO(User user);

    @Mapping(source = "roles", target = "roles", qualifiedByName = "StringToRole")
    User fromDTO(UserDTO userDTO);

    @Named("RoleToString")
    static Set<String> mapRolesToStrings(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }

    @Named("StringToRole")
    static Set<Role> mapStringsToRoles(Set<String> roles) {
        return roles.stream()
                .map(roleStr -> Role.builder().name(ERole.valueOf(roleStr)).build())
                .collect(Collectors.toSet());
    }
}
