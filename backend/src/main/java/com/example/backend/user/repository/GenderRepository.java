package com.example.backend.user.repository;

import com.example.backend.user.model.EGender;
import com.example.backend.user.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenderRepository extends JpaRepository<Gender, Long> {
    Optional<Gender> findByName(EGender valueOf);

}
