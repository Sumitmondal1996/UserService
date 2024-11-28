package com.scaler.userservice.models;

import com.scaler.userservice.dtos.TokenResponseDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity(name= "tokens")
public class Token extends BaseModel{
    private String token;
    private Date expireAt;
    @ManyToOne
    private User user;

    public static TokenResponseDto createtokenResponseDto(Token token)
    {
        if(token==null)
            return null;
        TokenResponseDto tokenResponseDto = new TokenResponseDto();
        tokenResponseDto.setToken(token.getToken());
        tokenResponseDto.setExpiryDate(token.getExpireAt());
        tokenResponseDto.setEmail(token.getUser().getEmail());
        return tokenResponseDto;

    }
}
