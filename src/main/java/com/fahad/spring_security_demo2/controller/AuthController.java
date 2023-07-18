package com.fahad.spring_security_demo2.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authenticationService.registerNewUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> LoginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
    }

}
