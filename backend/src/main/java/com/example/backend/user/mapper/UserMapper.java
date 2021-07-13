package com.example.backend.user.mapper;

import com.example.backend.user.model.*;
import com.example.backend.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "traits", target = "traits", qualifiedByName = "TraitToString")
    @Mapping(source="gender", target = "gender", qualifiedByName = "GenderToString")
    UserDTO toDTO(User user);

    @Mapping(source = "traits", target = "traits", qualifiedByName = "StringToTrait")
    @Mapping(source="gender", target = "gender", qualifiedByName = "StringToGender")
    User fromDTO(UserDTO userDTO);

    @Named("TraitToString")
    static Set<String> mapTraitsToStrings(Set<Trait> traits) {
        return traits.stream()
                .map(trait -> trait.getName().name())
                .collect(Collectors.toSet());
    }

    @Named("StringToTrait")
    static Set<Trait> mapStringToTraits(Set<String> traits) {
        return traits.stream()
                .map(traitStr -> Trait.builder().name(ETrait.valueOf(traitStr)).build())
                .collect(Collectors.toSet());
    }

    @Named("GenderToString")
    static String mapGenderToString(Gender g) {
        return g.getName().name();
    }

    @Named("StringToGender")
    static Gender mapStringToGender(String g) {
        return Gender.builder().name(EGender.valueOf(g)).build();
    }
}
