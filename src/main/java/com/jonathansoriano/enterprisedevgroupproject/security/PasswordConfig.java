package com.jonathansoriano.enterprisedevgroupproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Standalone configuration class for the PasswordEncoder bean.
 * This is intentionally separate from SecurityConfig to break the
 * circular dependency between UserService (which needs PasswordEncoder)
 * and SecurityConfig (which needs UserService for authentication).
 */
@Configuration
public class PasswordConfig {

    /**
     * Provides the BCrypt password encoder bean, used throughout the application
     * for encoding and verifying user passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
