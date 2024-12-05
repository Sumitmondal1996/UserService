package com.scaler.userservice.service;

import com.scaler.userservice.Security.models.CustomUserDetails;
import com.scaler.userservice.models.User;
import com.scaler.userservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;
    public CustomUserDetailService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUseruser = userRepository.findByEmail(username);
        if(optionalUseruser.isEmpty())
        {
            throw  new UsernameNotFoundException("user not found");
        }
        User user = optionalUseruser.get();

        return  new CustomUserDetails(user);
    }
}
