package com.odeyalo.analog.neflix.subscriptionservice.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthenticatedUserInformation implements UserDetails {
    private final String id;
    private final String nickname;
    private final List<GrantedAuthority> roles;

    public AuthenticatedUserInformation(String id, String nickname, List<GrantedAuthority> roles) {
        this.id = id;
        this.nickname = nickname;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return nickname;
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
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "AuthenticatedUserInformation{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", roles=" + roles +
                '}';
    }
}
