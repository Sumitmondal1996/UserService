package com.scaler.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TokenResponseDto {
    private String token;
    private Date expiryDate;
    private String email;
}
