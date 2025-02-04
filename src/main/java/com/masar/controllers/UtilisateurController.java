package com.masar.controllers;

import com.masar.models.Utilisateur;
import com.masar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.UUID;

// DTO class for user creation/update
record UtilisateurDTO(
    String nom,
    String email,
    String motDePasse,
    String role,
    String numeroDeTelephone,
    String adresse
) {}

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable UUID id) {
        return utilisateurService.getUtilisateurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUtilisateur(@Valid @RequestBody UtilisateurDTO dto) {
        if (dto.motDePasse() == null || dto.motDePasse().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("error", "Le mot de passe ne peut pas être vide"));
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(dto.nom());
        utilisateur.setEmail(dto.email());
        utilisateur.setMotDePasse(dto.motDePasse());
        utilisateur.setRole(dto.role());
        utilisateur.setNumeroDeTelephone(dto.numeroDeTelephone());
        utilisateur.setAdresse(dto.adresse());

        return ResponseEntity.ok(utilisateurService.createUtilisateur(utilisateur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUtilisateur(@PathVariable UUID id, @Valid @RequestBody UtilisateurDTO dto) {
        return utilisateurService.getUtilisateurById(id)
            .map(existingUser -> {
                if (dto.nom() != null) existingUser.setNom(dto.nom());
                if (dto.email() != null) existingUser.setEmail(dto.email());
                if (dto.motDePasse() != null && !dto.motDePasse().trim().isEmpty()) {
                    existingUser.setMotDePasse(dto.motDePasse());
                }
                if (dto.role() != null) existingUser.setRole(dto.role());
                if (dto.numeroDeTelephone() != null) existingUser.setNumeroDeTelephone(dto.numeroDeTelephone());
                if (dto.adresse() != null) existingUser.setAdresse(dto.adresse());
                
                return ResponseEntity.ok(utilisateurService.createUtilisateur(existingUser));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable UUID id) {
        return utilisateurService.deleteUtilisateur(id) 
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }
} 