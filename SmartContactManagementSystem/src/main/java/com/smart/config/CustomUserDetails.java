package com.smart.config;

import com.smart.data.transfer.objects.UserDto;
import com.smart.domain.UserDomain;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private UserDomain userDomain;

    public CustomUserDetails(UserDomain userDomain) {
        super();
        this.userDomain = userDomain;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userDomain.getUserRole());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return userDomain.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userDomain.getUserEmail();
    };

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
}
