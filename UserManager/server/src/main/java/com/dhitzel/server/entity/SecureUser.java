package com.dhitzel.server.entity;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class SecureUser extends User {
    private Role role;

    public SecureUser(String username, String password, List<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
