package com.masar.services;

import com.masar.models.Utilisateur;
import com.masar.repositories.UtilisateurRepository;
import com.masar.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UtilisateurService {
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(UUID id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> updateUtilisateur(UUID id, Utilisateur utilisateur) {
        if (utilisateurRepository.existsById(id)) {
            utilisateur.setId(id);
            if (utilisateur.getMotDePasse() != null) {
                utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            }
            return Optional.of(utilisateurRepository.save(utilisateur));
        }
        return Optional.empty();
    }

    public boolean deleteUtilisateur(UUID id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Map<String, Object> seConnecter(String email, String motDePasse) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, motDePasse)
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("id", utilisateur.getId());
        response.put("email", utilisateur.getEmail());
        response.put("role", utilisateur.getRole());
        
        return response;
    }

    public void seDeconnecter() {
        SecurityContextHolder.clearContext();
    }
} 