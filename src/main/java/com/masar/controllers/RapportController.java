package com.masar.controllers;

import com.masar.models.Rapport;
import com.masar.services.RapportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rapports")
public class RapportController {

    @Autowired
    private RapportService rapportService;

    @GetMapping
    public List<Rapport> getAllRapports() {
        return rapportService.getAllRapports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rapport> getRapportById(@PathVariable UUID id) {
        return rapportService.getRapportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-rapport/{idRapport}")
    public ResponseEntity<Rapport> getRapportByIdRapport(@PathVariable String idRapport) {
        return rapportService.getRapportByIdRapport(idRapport)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/genere-par/{generePar}")
    public List<Rapport> getRapportsByGenerePar(@PathVariable String generePar) {
        return rapportService.getRapportsByGenerePar(generePar);
    }

    @GetMapping("/date/{date}")
    public List<Rapport> getRapportsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return rapportService.getRapportsByDate(date);
    }

    @GetMapping("/type/{typeRapport}")
    public List<Rapport> getRapportsByTypeRapport(@PathVariable String typeRapport) {
        return rapportService.getRapportsByTypeRapport(typeRapport);
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public List<Rapport> getRapportsByUtilisateur(@PathVariable String utilisateurId) {
        return rapportService.getRapportsByUtilisateur(utilisateurId);
    }

    @GetMapping("/periode")
    public List<Rapport> getRapportsByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        return rapportService.getRapportsByPeriode(dateDebut, dateFin);
    }

    @PostMapping
    public Rapport createRapport(@RequestBody Rapport rapport) {
        return rapportService.createRapport(rapport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rapport> updateRapport(@PathVariable UUID id, @RequestBody Rapport rapport) {
        return rapportService.updateRapport(id, rapport)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRapport(@PathVariable UUID id) {
        return rapportService.deleteRapport(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/generer")
    public ResponseEntity<Rapport> genererRapport(
            @RequestParam String generePar,
            @RequestParam String typeRapport,
            @RequestParam String contenu,
            @RequestParam(required = false) String utilisateurId) {
        try {
            Rapport rapport = rapportService.genererRapport(generePar, typeRapport, contenu, utilisateurId);
            return ResponseEntity.ok(rapport);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 