package com.fahad.spring_security_demo2.controller;

import com.fahad.spring_security_demo2.model.Role;

public record RegisterRequest(
        String firstName,

        String lastName,

        String email,
        String password,
        Role role
) {

}
