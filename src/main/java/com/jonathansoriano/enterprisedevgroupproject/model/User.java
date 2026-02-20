package com.jonathansoriano.enterprisedevgroupproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Represents an application user (student/administrator) with Spring Security
 * integration.
 * Implements UserDetails to be compatible with Spring Security's authentication
 * system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private boolean enabled;

    /**
     * Returns the granted authorities for this user. All users have the ROLE_USER
     * authority.
     * 
     * @return Collection of granted authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    /**
     * Returns the password used to authenticate the user.
     * 
     * @return the hashed password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username (email address) used to authenticate the user.
     * 
     * @return the user's university email address
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indicates whether the user's account has expired. Always returns true
     * (non-expired).
     * 
     * @return true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. Always returns true
     * (non-locked).
     * 
     * @return true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired. Always returns true
     * (non-expired).
     * 
     * @return true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * 
     * @return true if the user is enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
