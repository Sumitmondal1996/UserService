package com.scaler.userservice.service;

import com.scaler.userservice.exceptions.TokenNotFoundException;
import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.repository.TokenRepository;
import com.scaler.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private String token;
    private boolean active;
    private Date expiryAt;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty())
        {
            return null;
        }

        User user = optionalUser.get();
        String savedpwd = user.getPassword();
        if(bCryptPasswordEncoder.matches(password,savedpwd))
        {
            Token token = new Token();
            LocalDateTime currentDateTime = LocalDateTime.now();

            // Token validity duration (e.g., 30 days)
            int tokenValidityDays = 30;

            // Calculate expiry date and time
            LocalDateTime expiryDateTime = currentDateTime.plus(tokenValidityDays, ChronoUnit.DAYS);

            // Convert LocalDateTime to Date
            Date expiryDate = Date.from(expiryDateTime.atZone(ZoneId.systemDefault()).toInstant());

            // Set the expiry date
            token.setExpireAt(expiryDate);
            token.setUser(user);

            // set the token value randomly

            token.setToken(RandomStringUtils.randomAlphanumeric(128));

            return tokenRepository.save(token);
        }
        return null;
    }

    @Override
    public User signup(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return user;
        }
        else
        {
            User user = new User();
            user.setEmail(email);
            String encodedPassword = bCryptPasswordEncoder.encode(password);
            user.setPassword(encodedPassword);
            user.setVerified(true);
            user.setUsername(email);
            User saveduser= userRepository.save(user);
            return saveduser;
        }
    }

    @Override
    public void logout(String token, boolean active, Date ExpiryAt) throws TokenNotFoundException {
        Optional<Token> optionalToken = tokenRepository.findByTokenAndActiveAndExpireAtGreaterThan(token, active, ExpiryAt);
        if(optionalToken.isEmpty())
        {
            throw new TokenNotFoundException("Token Not Found");
        }

        Token token1 = optionalToken.get();
        token1.setActive(false);
         tokenRepository.save(token1);
         return;

    }

    @Override
    public User validateToken(String token, boolean active, Date ExpiryAt) throws TokenNotFoundException {

        Optional<Token> optionalToken = tokenRepository.findByTokenAndActiveAndExpireAtGreaterThan(token, active, ExpiryAt);
        if(optionalToken.isEmpty())
        {
            throw new TokenNotFoundException("Token Not Found");
        }
        return optionalToken.get().getUser();

    }
}
