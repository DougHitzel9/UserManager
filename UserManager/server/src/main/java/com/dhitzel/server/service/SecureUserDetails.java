package com.dhitzel.server.service;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dhitzel.server.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SecureUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(SecureUserDetails.class);

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    public SecureUserDetails(Long id, String username, String password) {
        logger.info("*** SecureUserDetails() - username: " + username + " password: " + password);

        this.id = id;
        this.username = username;
        this.password = password;

    }

    public static SecureUserDetails build(User user) {
        return new SecureUserDetails(user.getUserId(), user.getUsername(), user.getPassword());
    }

    public Long getId() {
        return id;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        logger.info("*** getPassword() - " + password);
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SecureUserDetails user = (SecureUserDetails) o;
        return Objects.equals(id, user.id);
    }
}