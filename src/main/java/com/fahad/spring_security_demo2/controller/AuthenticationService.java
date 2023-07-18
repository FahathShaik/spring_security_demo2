package com.fahad.spring_security_demo2.controller;

import com.fahad.spring_security_demo2.model.Role;
import com.fahad.spring_security_demo2.model.User;
import com.fahad.spring_security_demo2.repository.UserRepository;
import com.fahad.spring_security_demo2.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerNewUser(RegisterRequest registerRequest) {

        var user = new User(registerRequest.firstName(), registerRequest.lastName(),
                registerRequest.email(), passwordEncoder.encode(registerRequest.password()) , Role.USER);
        userRepository.save(user);

        return new RegisterResponse(jwtService.generateToken(user));
    }

    public LoginResponse authenticateUser(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()
        ));

        var user = userDetailsService.loadUserByUsername(loginRequest.email());
        return new LoginResponse(jwtService.generateToken(user));
    }
}
