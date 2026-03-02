package com.steph.auth;


import com.steph.config.JwtService;
import com.steph.user.Role;
import com.steph.user.UserRepository;
import com.steph.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Create a user, save to database and return the generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );
        userRepository.save(user);
        return new AuthenticationResponse(jwtService.generateToken(user), jwtService.generateRefreshToken(user));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }
}
