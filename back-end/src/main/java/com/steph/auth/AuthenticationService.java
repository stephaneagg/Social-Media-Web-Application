package com.steph.auth;


import com.steph.config.JwtService;
import com.steph.exceptions.DuplicateResourceException;
import com.steph.exceptions.UserException;
import com.steph.user.Role;
import com.steph.user.UserRepository;
import com.steph.user.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // Create a user, save to database and return the generated token out of it
    public AuthenticationResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail()) || userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateResourceException("Email Username is already in use");
        }

        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );
        userRepository.save(user);
        return new AuthenticationResponse(jwtService.generateToken(user, user.getId()));
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmailOrUsername(request.getLogin(), request.getLogin())
                .orElseThrow(() -> new UserException("User does not exist"));
        return new AuthenticationResponse(jwtService.generateToken(user, user.getId()));
    }
}
