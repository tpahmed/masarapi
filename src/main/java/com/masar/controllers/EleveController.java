package com.masar.controllers;

import com.masar.models.Eleve;
import com.masar.models.Note;
import com.masar.models.Presence;
import com.masar.models.Ressource;
import com.masar.services.EleveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @GetMapping
    public List<Eleve> getAllEleves() {
        return eleveService.getAllEleves();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable UUID id) {
        return eleveService.getEleveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-eleve/{idEleve}")
    public ResponseEntity<Eleve> getEleveByIdEleve(@PathVariable String idEleve) {
        return eleveService.getEleveByIdEleve(idEleve)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/niveau/{niveau}")
    public List<Eleve> getElevesByNiveau(@PathVariable String niveau) {
        return eleveService.getElevesByNiveau(niveau);
    }

    @GetMapping("/classe/{classe}")
    public List<Eleve> getElevesByClasse(@PathVariable String classe) {
        return eleveService.getElevesByClasse(classe);
    }

    @GetMapping("/annee-inscription/{date}")
    public List<Eleve> getElevesByAnneeInscription(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return eleveService.getElevesByAnneeInscription(date);
    }

    @GetMapping("/classe-niveau")
    public List<Eleve> getElevesByClasseAndNiveau(
            @RequestParam String classe,
            @RequestParam String niveau) {
        return eleveService.getElevesByClasseAndNiveau(classe, niveau);
    }

    @PostMapping
    public Eleve createEleve(@RequestBody Eleve eleve) {
        return eleveService.createEleve(eleve);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable UUID id, @RequestBody Eleve eleve) {
        return eleveService.updateEleve(id, eleve)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable UUID id) {
        return eleveService.deleteEleve(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<List<Note>> consulterNotes(@PathVariable UUID id) {
        try {
            List<Note> notes = eleveService.consulterNotes(id);
            return ResponseEntity.ok(notes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/notes/matiere/{matiere}")
    public ResponseEntity<List<Note>> consulterNotesByMatiere(
            @PathVariable UUID id,
            @PathVariable String matiere) {
        try {
            List<Note> notes = eleveService.consulterNotesByMatiere(id, matiere);
            return ResponseEntity.ok(notes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/presence")
    public ResponseEntity<List<Presence>> consulterPresence(@PathVariable UUID id) {
        try {
            List<Presence> presences = eleveService.consulterPresence(id);
            return ResponseEntity.ok(presences);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/presence/periode")
    public ResponseEntity<List<Presence>> consulterPresenceParPeriode(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        try {
            List<Presence> presences = eleveService.consulterPresenceParPeriode(id, dateDebut, dateFin);
            return ResponseEntity.ok(presences);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/ressources")
    public ResponseEntity<List<Ressource>> accederRessources(@PathVariable UUID id) {
        try {
            List<Ressource> ressources = eleveService.accederRessources(id);
            return ResponseEntity.ok(ressources);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/ressources/matiere/{matiere}")
    public ResponseEntity<List<Ressource>> accederRessourcesByMatiere(
            @PathVariable UUID id,
            @PathVariable String matiere) {
        try {
            List<Ressource> ressources = eleveService.accederRessourcesByMatiere(id, matiere);
            return ResponseEntity.ok(ressources);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/ressources/recentes")
    public ResponseEntity<List<Ressource>> accederRessourcesRecentes(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date depuis) {
        try {
            List<Ressource> ressources = eleveService.accederRessourcesRecentes(id, depuis);
            return ResponseEntity.ok(ressources);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 