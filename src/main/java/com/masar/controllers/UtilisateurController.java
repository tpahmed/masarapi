package com.masar.controllers;

import com.masar.models.Utilisateur;
import com.masar.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.createUtilisateur(utilisateur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable UUID id, @RequestBody Utilisateur utilisateur) {
        return utilisateurService.updateUtilisateur(id, utilisateur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable UUID id) {
        return utilisateurService.deleteUtilisateur(id) 
            ? ResponseEntity.ok().build()
            : ResponseEntity.notFound().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> seConnecter(@RequestParam String email, @RequestParam String motDePasse) {
        try {
            Map<String, Object> response = utilisateurService.seConnecter(email, motDePasse);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> seDeconnecter() {
        utilisateurService.seDeconnecter();
        return ResponseEntity.ok().build();
    }
} 