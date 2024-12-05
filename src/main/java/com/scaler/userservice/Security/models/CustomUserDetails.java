package com.scaler.userservice.Security.models;

import com.scaler.userservice.models.Role;
import com.scaler.userservice.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {
    private String password;
    private String username;
    private List<GrantedAuthority> authorities;
    private  boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    public  CustomUserDetails(User user)
    {
           this.password = user.getPassword();
            this.username=user.getUsername();
            this.accountNonExpired = true;
            this.accountNonLocked= true;
            this.credentialsNonExpired= true;
            this.enabled= true;


            List<Role> roleList = user.getRoles();
            authorities = new ArrayList<>();
            for(Role role: roleList)
            {
                authorities.add(new CustomgrantedAuthority(role));
            }
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
