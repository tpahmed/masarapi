package com.masar.controllers;

import com.masar.models.Parent;
import com.masar.models.Eleve;
import com.masar.models.Note;
import com.masar.models.Presence;
import com.masar.models.Ressource;
import com.masar.services.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/parents")
public class ParentController {

    @Autowired
    private ParentService parentService;

    @GetMapping
    public List<Parent> getAllParents() {
        return parentService.getAllParents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parent> getParentById(@PathVariable UUID id) {
        return parentService.getParentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-parent/{idParent}")
    public ResponseEntity<Parent> getParentByIdParent(@PathVariable String idParent) {
        return parentService.getParentByIdParent(idParent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/profession/{profession}")
    public List<Parent> getParentsByProfession(@PathVariable String profession) {
        return parentService.getParentsByProfession(profession);
    }

    @GetMapping("/nombre-enfants/{nombre}")
    public List<Parent> getParentsByNombreEnfants(@PathVariable Integer nombre) {
        return parentService.getParentsByNombreEnfants(nombre);
    }

    @GetMapping("/nombre-enfants-min")
    public List<Parent> getParentsByNombreEnfantsMin(@RequestParam Integer nombreMin) {
        return parentService.getParentsByNombreEnfantsMin(nombreMin);
    }

    @PostMapping
    public Parent createParent(@RequestBody Parent parent) {
        return parentService.createParent(parent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parent> updateParent(
            @PathVariable UUID id,
            @RequestBody Parent parent) {
        return parentService.updateParent(id, parent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParent(@PathVariable UUID id) {
        return parentService.deleteParent(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<Void> consulterNotes(@PathVariable UUID id) {
        try {
            parentService.consulterNotes(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/presence")
    public ResponseEntity<Void> consulterPresence(@PathVariable UUID id) {
        try {
            parentService.consulterPresence(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants")
    public ResponseEntity<List<Eleve>> getEnfants(@PathVariable UUID parentId) {
        try {
            List<Eleve> enfants = parentService.getEnfants(parentId);
            return ResponseEntity.ok(enfants);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/notes")
    public ResponseEntity<List<Note>> consulterNotesEnfant(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId) {
        try {
            List<Note> notes = parentService.consulterNotesEnfant(parentId, eleveId);
            return ResponseEntity.ok(notes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/notes/matiere/{matiere}")
    public ResponseEntity<List<Note>> consulterNotesByMatiere(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId,
            @PathVariable String matiere) {
        try {
            List<Note> notes = parentService.consulterNotesByMatiere(parentId, eleveId, matiere);
            return ResponseEntity.ok(notes);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/presences")
    public ResponseEntity<List<Presence>> consulterPresenceEnfant(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId) {
        try {
            List<Presence> presences = parentService.consulterPresenceEnfant(parentId, eleveId);
            return ResponseEntity.ok(presences);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/presences/periode")
    public ResponseEntity<List<Presence>> consulterPresenceParPeriode(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        try {
            List<Presence> presences = parentService.consulterPresenceParPeriode(parentId, eleveId, dateDebut, dateFin);
            return ResponseEntity.ok(presences);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/ressources")
    public ResponseEntity<List<Ressource>> consulterRessourcesEnfant(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId) {
        try {
            List<Ressource> ressources = parentService.consulterRessourcesEnfant(parentId, eleveId);
            return ResponseEntity.ok(ressources);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/ressources/matiere/{matiere}")
    public ResponseEntity<List<Ressource>> consulterRessourcesByMatiere(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId,
            @PathVariable String matiere) {
        try {
            List<Ressource> ressources = parentService.consulterRessourcesByMatiere(parentId, eleveId, matiere);
            return ResponseEntity.ok(ressources);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{parentId}/enfants/{eleveId}/ressources/recentes")
    public ResponseEntity<List<Ressource>> consulterRessourcesRecentes(
            @PathVariable UUID parentId,
            @PathVariable UUID eleveId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date depuis) {
        try {
            List<Ressource> ressources = parentService.consulterRessourcesRecentes(parentId, eleveId, depuis);
            return ResponseEntity.ok(ressources);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 