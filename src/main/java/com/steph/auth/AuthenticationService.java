package com.steph.auth;


import com.steph.user.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a user, save to database and return the generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User()
        return null;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
