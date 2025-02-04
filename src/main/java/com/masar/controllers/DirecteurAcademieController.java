package com.masar.controllers;

import com.masar.models.DirecteurAcademie;
import com.masar.models.Rapport;
import com.masar.services.DirecteurAcademieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/directeurs-academie")
public class DirecteurAcademieController {

    @Autowired
    private DirecteurAcademieService directeurAcademieService;

    @GetMapping
    public List<DirecteurAcademie> getAllDirecteursAcademie() {
        return directeurAcademieService.getAllDirecteursAcademie();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DirecteurAcademie> getDirecteurAcademieById(@PathVariable UUID id) {
        return directeurAcademieService.getDirecteurAcademieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{region}")
    public List<DirecteurAcademie> getDirecteursAcademieByRegion(@PathVariable String region) {
        return directeurAcademieService.getDirecteursAcademieByRegion(region);
    }

    @GetMapping("/academie/{nomAcademie}")
    public ResponseEntity<DirecteurAcademie> getDirecteurAcademieByNomAcademie(@PathVariable String nomAcademie) {
        return directeurAcademieService.getDirecteurAcademieByNomAcademie(nomAcademie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DirecteurAcademie createDirecteurAcademie(@RequestBody DirecteurAcademie directeurAcademie) {
        return directeurAcademieService.createDirecteurAcademie(directeurAcademie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DirecteurAcademie> updateDirecteurAcademie(
            @PathVariable UUID id,
            @RequestBody DirecteurAcademie directeurAcademie) {
        return directeurAcademieService.updateDirecteurAcademie(id, directeurAcademie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirecteurAcademie(@PathVariable UUID id) {
        return directeurAcademieService.deleteDirecteurAcademie(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/rapports")
    public ResponseEntity<Rapport> genererRapports(
            @PathVariable UUID id,
            @RequestParam String typeRapport,
            @RequestParam String contenu) {
        try {
            Rapport rapport = directeurAcademieService.genererRapports(id, typeRapport, contenu);
            return ResponseEntity.ok(rapport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/definir-politiques")
    public ResponseEntity<Rapport> definirPolitiques(
            @PathVariable UUID id,
            @RequestParam String politiques) {
        try {
            Rapport rapport = directeurAcademieService.definirPolitiques(id, politiques);
            return ResponseEntity.ok(rapport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 