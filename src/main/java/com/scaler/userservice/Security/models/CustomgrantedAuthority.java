package com.scaler.userservice.Security.models;

import com.scaler.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomgrantedAuthority implements GrantedAuthority {
    private Role role;
    public CustomgrantedAuthority(Role role)
    {
        this.role= role;
    }


    @Override
    public String getAuthority() {
        return this.role.getRoleName();
    }
}
