package com.scaler.userservice.dtos;

import com.scaler.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LogOutRequestdto {
    private String token;

}
