package com.scaler.userservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.scaler.userservice.dtos.*;
import com.scaler.userservice.exceptions.TokenNotFoundException;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping ("/login")
    public TokenResponseDto login(@RequestBody LoginRequestdto loginRequestdto)
    {
        Token token = userService.login(
                loginRequestdto.getEmail(),
                loginRequestdto.getPassword()
        );

        return Token.createtokenResponseDto(token);
    }
    @PostMapping("/signup")
    public UserResponsedto signup(@RequestBody UserRequestdto userRequestdto) throws JsonProcessingException {
        User user=  userService.signup(
                userRequestdto.getEmail(),
                userRequestdto.getPassword()
        );
        return User.toUserResponsedto(user);
    }
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogOutRequestdto logoutRequestdto) throws TokenNotFoundException {
        ResponseEntity<Void> responseEntity = null;

        try {
            userService.logout(
                    logoutRequestdto.getToken(),
                    true,
                    new Date()

            );

            responseEntity = new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (TokenNotFoundException e) {
            responseEntity = new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );

        }
        return  responseEntity;
    }
    @GetMapping("/validate/{tokenValue}")
    public UserResponsedto validateToken(@PathVariable("tokenValue") String token) throws TokenNotFoundException {
        try {
            User user = userService.validateToken(token, true, new Date());
            return User.toUserResponsedto(user);

        }
        catch (TokenNotFoundException e)
        {
            throw new TokenNotFoundException("Token Not Found");
        }


    }
}
