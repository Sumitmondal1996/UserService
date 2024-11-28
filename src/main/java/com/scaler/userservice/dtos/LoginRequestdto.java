package com.scaler.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestdto {
    private String email;
    private String password;
}
