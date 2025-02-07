package com.masar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private String role;
    private String nom;

    public JwtResponse(String token, String email, String role, String nom) {
        this.token = token;
        this.email = email;
        this.role = role;
        this.nom = nom;
    }
} 