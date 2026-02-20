package com.jonathansoriano.enterprisedevgroupproject.service;

import com.jonathansoriano.enterprisedevgroupproject.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for user authentication and registration operations.
 * Implements UserDetailsService to integrate with Spring Security's login flow.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Loads a user by their email address (used as the username).
     * Required by Spring Security's authentication mechanism.
     *
     * @param email the user's university email
     * @return UserDetails object for Spring Security
     * @throws UsernameNotFoundException if no user found with the given email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    /**
     * Registers a new user with the given email and password.
     * The password is BCrypt-hashed before storage.
     *
     * @param email    the university email address (must end in .edu)
     * @param password the raw plaintext password chosen by the user
     * @throws IllegalArgumentException if the email is already registered
     */
    public void register(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
        String hashedPassword = passwordEncoder.encode(password);
        userRepository.save(email, hashedPassword);
    }

    /**
     * Checks if an email address is already registered.
     *
     * @param email the email to check
     * @return true if the email is in use, false otherwise
     */
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
