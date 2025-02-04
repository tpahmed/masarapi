package com.masar.controllers;

import com.masar.models.Presence;
import com.masar.services.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    @Autowired
    private PresenceService presenceService;

    @GetMapping
    public List<Presence> getAllPresences() {
        return presenceService.getAllPresences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Presence> getPresenceById(@PathVariable UUID id) {
        return presenceService.getPresenceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-presence/{idPresence}")
    public ResponseEntity<Presence> getPresenceByIDPresence(@PathVariable String idPresence) {
        return presenceService.getPresenceByIDPresence(idPresence)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/date/{date}")
    public List<Presence> getPresencesByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return presenceService.getPresencesByDate(date);
    }

    @GetMapping("/statut/{statut}")
    public List<Presence> getPresencesByStatut(@PathVariable String statut) {
        return presenceService.getPresencesByStatut(statut);
    }

    @GetMapping("/eleve/{idEleve}")
    public List<Presence> getPresencesByEleve(@PathVariable String idEleve) {
        return presenceService.getPresencesByEleve(idEleve);
    }

    @GetMapping("/enseignant/{idEnseignant}")
    public List<Presence> getPresencesByEnseignant(@PathVariable String idEnseignant) {
        return presenceService.getPresencesByEnseignant(idEnseignant);
    }

    @GetMapping("/eleve-id/{eleveId}")
    public List<Presence> getPresencesByEleveId(@PathVariable UUID eleveId) {
        return presenceService.getPresencesByEleveId(eleveId);
    }

    @GetMapping("/enseignant-id/{enseignantId}")
    public List<Presence> getPresencesByEnseignantId(@PathVariable UUID enseignantId) {
        return presenceService.getPresencesByEnseignantId(enseignantId);
    }

    @GetMapping("/periode")
    public List<Presence> getPresencesByPeriode(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        return presenceService.getPresencesByPeriode(dateDebut, dateFin);
    }

    @GetMapping("/eleve/{idEleve}/periode")
    public List<Presence> getPresencesByEleveAndPeriode(
            @PathVariable String idEleve,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        return presenceService.getPresencesByEleveAndPeriode(idEleve, dateDebut, dateFin);
    }

    @PostMapping
    public Presence ajouterPresence(@RequestBody Presence presence) {
        return presenceService.ajouterPresence(presence);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Presence> modifierPresence(
            @PathVariable UUID id,
            @RequestBody Presence presence) {
        return presenceService.modifierPresence(id, presence)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerPresence(@PathVariable UUID id) {
        return presenceService.supprimerPresence(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
} 