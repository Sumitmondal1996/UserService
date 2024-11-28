package com.scaler.userservice.service;

import com.scaler.userservice.exceptions.TokenNotFoundException;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;

import java.util.Date;

public interface UserService {
    Token login(String username, String password);
    User signup(String username, String password);
    void logout(String token,boolean active, Date ExpiryAt) throws TokenNotFoundException;
    User validateToken(String token,boolean active, Date ExpiryAt) throws TokenNotFoundException;
}
