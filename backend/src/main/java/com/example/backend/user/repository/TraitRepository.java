package com.example.backend.user.repository;

import com.example.backend.user.model.ETrait;
import com.example.backend.user.model.Trait;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TraitRepository extends JpaRepository<Trait, Long> {
    Optional<Trait> findByName(ETrait valueOf);
}
