package com.masar.controllers;

import com.masar.models.Delegue;
import com.masar.models.Rapport;
import com.masar.services.DelegueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/delegues")
public class DelegueController {

    @Autowired
    private DelegueService delegueService;

    @GetMapping
    public List<Delegue> getAllDelegues() {
        return delegueService.getAllDelegues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Delegue> getDelegueById(@PathVariable UUID id) {
        return delegueService.getDelegueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/region/{region}")
    public List<Delegue> getDeleguesByRegion(@PathVariable String region) {
        return delegueService.getDeleguesByRegion(region);
    }

    @PostMapping
    public Delegue createDelegue(@RequestBody Delegue delegue) {
        return delegueService.createDelegue(delegue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Delegue> updateDelegue(@PathVariable UUID id, @RequestBody Delegue delegue) {
        return delegueService.updateDelegue(id, delegue)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelegue(@PathVariable UUID id) {
        return delegueService.deleteDelegue(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/rapports")
    public ResponseEntity<Rapport> genererRapports(
            @PathVariable UUID id,
            @RequestParam String typeRapport,
            @RequestParam String contenu) {
        try {
            Rapport rapport = delegueService.genererRapports(id, typeRapport, contenu);
            return ResponseEntity.ok(rapport);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rapports")
    public ResponseEntity<List<Rapport>> getRapportsByDelegue(@PathVariable UUID id) {
        try {
            List<Rapport> rapports = delegueService.getRapportsByDelegue(id);
            return ResponseEntity.ok(rapports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rapports/periode")
    public ResponseEntity<List<Rapport>> getRapportsByDelegueAndPeriode(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin) {
        try {
            List<Rapport> rapports = delegueService.getRapportsByDelegueAndPeriode(id, dateDebut, dateFin);
            return ResponseEntity.ok(rapports);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/region/{region}/rapports")
    public ResponseEntity<List<Rapport>> getRapportsByRegion(@PathVariable String region) {
        List<Rapport> rapports = delegueService.getRapportsByRegion(region);
        return ResponseEntity.ok(rapports);
    }
} 