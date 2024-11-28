package com.scaler.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestdto {
    private String username;
    private String email;
    private String password;
}
