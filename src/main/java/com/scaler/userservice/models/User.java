package com.scaler.userservice.models;

import com.scaler.userservice.dtos.UserResponsedto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name="users")
public class User extends BaseModel{
    private String username;
    private String email;
    private String password;
    private boolean verified;
    @ManyToMany
    private List<Role> roles;

    public static UserResponsedto toUserResponsedto(User user) {
        if (user == null)
            return null;
        UserResponsedto userdto = new UserResponsedto();
        userdto.setUsername(user.getUsername());
        return userdto;

    }
}
