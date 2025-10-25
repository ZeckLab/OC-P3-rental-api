package com.openclassrooms.rentalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.openclassrooms.rentalapi.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
