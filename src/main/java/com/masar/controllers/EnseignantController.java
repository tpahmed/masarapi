package com.masar.controllers;

import com.masar.models.Enseignant;
import com.masar.models.Note;
import com.masar.models.Presence;
import com.masar.models.Ressource;
import com.masar.services.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @GetMapping
    public List<Enseignant> getAllEnseignants() {
        return enseignantService.getAllEnseignants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable UUID id) {
        return enseignantService.getEnseignantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-employe/{idEmploye}")
    public ResponseEntity<Enseignant> getEnseignantByIdEmploye(@PathVariable String idEmploye) {
        return enseignantService.getEnseignantByIdEmploye(idEmploye)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matiere/{matiere}")
    public List<Enseignant> getEnseignantsByMatiere(@PathVariable String matiere) {
        return enseignantService.getEnseignantsByMatiere(matiere);
    }

    @GetMapping("/qualification/{qualification}")
    public List<Enseignant> getEnseignantsByQualification(@PathVariable String qualification) {
        return enseignantService.getEnseignantsByQualification(qualification);
    }

    @GetMapping("/experience")
    public List<Enseignant> getEnseignantsByExperienceMin(@RequestParam Integer anneesMin) {
        return enseignantService.getEnseignantsByExperienceMin(anneesMin);
    }

    @GetMapping("/matiere-qualification")
    public List<Enseignant> getEnseignantsByMatiereAndQualification(
            @RequestParam String matiere,
            @RequestParam String qualification) {
        return enseignantService.getEnseignantsByMatiereAndQualification(matiere, qualification);
    }

    @PostMapping
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant) {
        return enseignantService.createEnseignant(enseignant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(
            @PathVariable UUID id,
            @RequestBody Enseignant enseignant) {
        return enseignantService.updateEnseignant(id, enseignant)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable UUID id) {
        return enseignantService.deleteEnseignant(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{enseignantId}/notes")
    public ResponseEntity<Note> createNote(
            @PathVariable UUID enseignantId,
            @RequestBody Note note) {
        try {
            Note createdNote = enseignantService.createNote(enseignantId, note);
            return ResponseEntity.ok(createdNote);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{enseignantId}/notes")
    public ResponseEntity<List<Note>> getAllNotes(@PathVariable UUID enseignantId) {
        List<Note> notes = enseignantService.getAllNotes(enseignantId);
        return ResponseEntity.ok(notes);
    }

    @GetMapping("/{enseignantId}/notes/{noteId}")
    public ResponseEntity<Note> getNoteById(
            @PathVariable UUID enseignantId,
            @PathVariable UUID noteId) {
        return enseignantService.getNoteById(enseignantId, noteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{enseignantId}/notes/matiere/{matiere}")
    public ResponseEntity<List<Note>> getNotesByMatiere(
            @PathVariable UUID enseignantId,
            @PathVariable String matiere) {
        try {
            List<Note> notes = enseignantService.getNotesByMatiere(enseignantId, matiere);
            return ResponseEntity.ok(notes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{enseignantId}/notes/eleve/{idEleve}")
    public ResponseEntity<List<Note>> getNotesByEleve(
            @PathVariable UUID enseignantId,
            @PathVariable String idEleve) {
        try {
            List<Note> notes = enseignantService.getNotesByEleve(enseignantId, idEleve);
            return ResponseEntity.ok(notes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{enseignantId}/notes/{noteId}")
    public ResponseEntity<Note> updateNote(
            @PathVariable UUID enseignantId,
            @PathVariable UUID noteId,
            @RequestBody Note note) {
        return enseignantService.updateNote(enseignantId, noteId, note)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{enseignantId}/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(
            @PathVariable UUID enseignantId,
            @PathVariable UUID noteId) {
        return enseignantService.deleteNote(enseignantId, noteId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{enseignantId}/presences")
    public ResponseEntity<Presence> createPresence(
            @PathVariable UUID enseignantId,
            @RequestBody Presence presence) {
        try {
            Presence createdPresence = enseignantService.createPresence(enseignantId, presence);
            return ResponseEntity.ok(createdPresence);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{enseignantId}/presences")
    public ResponseEntity<List<Presence>> getAllPresences(@PathVariable UUID enseignantId) {
        List<Presence> presences = enseignantService.getAllPresences(enseignantId);
        return ResponseEntity.ok(presences);
    }

    @GetMapping("/{enseignantId}/presences/{presenceId}")
    public ResponseEntity<Presence> getPresenceById(
            @PathVariable UUID enseignantId,
            @PathVariable UUID presenceId) {
        return enseignantService.getPresenceById(enseignantId, presenceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{enseignantId}/presences/eleve/{idEleve}")
    public ResponseEntity<List<Presence>> getPresencesByEleve(
            @PathVariable UUID enseignantId,
            @PathVariable String idEleve) {
        try {
            List<Presence> presences = enseignantService.getPresencesByEleve(enseignantId, idEleve);
            return ResponseEntity.ok(presences);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{enseignantId}/presences/periode")
    public ResponseEntity<List<Presence>> getPresencesByPeriode(
            @PathVariable UUID enseignantId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        try {
            List<Presence> presences = enseignantService.getPresencesByPeriode(enseignantId, dateDebut, dateFin);
            return ResponseEntity.ok(presences);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{enseignantId}/presences/{presenceId}")
    public ResponseEntity<Presence> updatePresence(
            @PathVariable UUID enseignantId,
            @PathVariable UUID presenceId,
            @RequestBody Presence presence) {
        return enseignantService.updatePresence(enseignantId, presenceId, presence)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{enseignantId}/presences/{presenceId}")
    public ResponseEntity<Void> deletePresence(
            @PathVariable UUID enseignantId,
            @PathVariable UUID presenceId) {
        return enseignantService.deletePresence(enseignantId, presenceId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{enseignantId}/ressources")
    public ResponseEntity<Ressource> createRessource(
            @PathVariable UUID enseignantId,
            @RequestBody Ressource ressource) {
        try {
            Ressource createdRessource = enseignantService.createRessource(enseignantId, ressource);
            return ResponseEntity.ok(createdRessource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{enseignantId}/ressources")
    public ResponseEntity<List<Ressource>> getAllRessources(@PathVariable UUID enseignantId) {
        List<Ressource> ressources = enseignantService.getAllRessources(enseignantId);
        return ResponseEntity.ok(ressources);
    }

    @GetMapping("/{enseignantId}/ressources/{ressourceId}")
    public ResponseEntity<Ressource> getRessourceById(
            @PathVariable UUID enseignantId,
            @PathVariable UUID ressourceId) {
        return enseignantService.getRessourceById(enseignantId, ressourceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{enseignantId}/ressources/{ressourceId}")
    public ResponseEntity<Ressource> updateRessource(
            @PathVariable UUID enseignantId,
            @PathVariable UUID ressourceId,
            @RequestBody Ressource ressource) {
        return enseignantService.updateRessource(enseignantId, ressourceId, ressource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{enseignantId}/ressources/{ressourceId}")
    public ResponseEntity<Void> deleteRessource(
            @PathVariable UUID enseignantId,
            @PathVariable UUID ressourceId) {
        return enseignantService.deleteRessource(enseignantId, ressourceId)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
} 