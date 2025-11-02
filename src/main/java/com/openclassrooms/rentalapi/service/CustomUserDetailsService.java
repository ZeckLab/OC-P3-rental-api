package com.openclassrooms.rentalapi.service;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.openclassrooms.rentalapi.model.AppUser;
import com.openclassrooms.rentalapi.repository.AppUserRepository;
import static com.openclassrooms.rentalapi.constants.ErrorMessages.*;

/**
 * Custom implementation of {@link UserDetailsService} used by Spring Security.
 * <p>
 * Loads user details from the database using the provided email address.
 * This service is invoked during authentication to retrieve user credentials
 * and roles.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND + email));

        return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
