package com.jonathansoriano.enterprisedevgroupproject.repository;

import com.jonathansoriano.enterprisedevgroupproject.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for performing CRUD operations on the users table.
 * Provides custom query methods for finding and saving users via email.
 */
@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Finds a user by their university email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the User if found, or empty if not
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT id, email, password, enabled FROM users WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
        try {
            User user = jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Checks whether an email address is already registered in the system.
     *
     * @param email the email address to check
     * @return true if the email is already in use, false otherwise
     */
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = :email";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    /**
     * Saves a new user to the database.
     *
     * @param email          the user's university email
     * @param hashedPassword the BCrypt-hashed password
     */
    public void save(String email, String hashedPassword) {
        String sql = "INSERT INTO users (email, password, enabled) VALUES (:email, :password, true)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("password", hashedPassword);
        jdbcTemplate.update(sql, params);
    }
}
