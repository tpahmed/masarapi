package com.masar.controllers;

import com.masar.dto.LoginRequest;
import com.masar.dto.SignupRequest;
import com.masar.dto.JwtResponse;
import com.masar.models.Utilisateur;
import com.masar.repositories.UtilisateurRepository;
import com.masar.security.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilisateurRepository utilisateurRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur user = utilisateurRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        
        return ResponseEntity.ok(new JwtResponse(jwt,
                                               userDetails.getUsername(),
                                               user.getRole(),
                                               user.getNom()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (utilisateurRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user account
        Utilisateur user = new Utilisateur();
        user.setNom(signUpRequest.getNom());
        user.setEmail(signUpRequest.getEmail());
        user.setMotDePasse(encoder.encode(signUpRequest.getPassword()));
        user.setRole(signUpRequest.getRole());
        user.setNumeroDeTelephone(signUpRequest.getNumeroDeTelephone());
        user.setAdresse(signUpRequest.getAdresse());

        utilisateurRepository.save(user);

        // Login the user after successful registration
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(signUpRequest.getEmail());
        loginRequest.setPassword(signUpRequest.getPassword());
        
        return authenticateUser(loginRequest);
    }
} 