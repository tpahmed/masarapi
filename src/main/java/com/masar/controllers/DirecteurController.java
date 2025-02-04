package com.masar.controllers;

import com.masar.models.Directeur;
import com.masar.models.Utilisateur;
import com.masar.models.Rapport;
import com.masar.services.DirecteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/directeurs")
public class DirecteurController {

    @Autowired
    private DirecteurService directeurService;

    @GetMapping
    public List<Directeur> getAllDirecteurs() {
        return directeurService.getAllDirecteurs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Directeur> getDirecteurById(@PathVariable UUID id) {
        return directeurService.getDirecteurById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Directeur createDirecteur(@RequestBody Directeur directeur) {
        return directeurService.createDirecteur(directeur);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Directeur> updateDirecteur(@PathVariable UUID id, @RequestBody Directeur directeur) {
        return directeurService.updateDirecteur(id, directeur)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirecteur(@PathVariable UUID id) {
        return directeurService.deleteDirecteur(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/utilisateurs")
    public ResponseEntity<Utilisateur> ajouterUtilisateur(
            @PathVariable UUID id,
            @RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newUtilisateur = directeurService.ajouterUtilisateur(id, utilisateur);
            return ResponseEntity.ok(newUtilisateur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/approuver-ressources")
    public ResponseEntity<Void> approuverRessources(@PathVariable UUID id) {
        try {
            directeurService.approuverRessources(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rapports")
    public ResponseEntity<List<Rapport>> getRapportsByDirecteur(@PathVariable UUID id) {
        try {
            List<Rapport> rapports = directeurService.getRapportsByDirecteur(id);
            return ResponseEntity.ok(rapports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rapports/periode")
    public ResponseEntity<List<Rapport>> getRapportsByDirecteurAndPeriode(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        try {
            List<Rapport> rapports = directeurService.getRapportsByDirecteurAndPeriode(id, dateDebut, dateFin);
            return ResponseEntity.ok(rapports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 