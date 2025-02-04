package com.masar.controllers;

import com.masar.models.Note;
import com.masar.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable UUID id) {
        return noteService.getNoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-note/{idNote}")
    public ResponseEntity<Note> getNoteByIDNote(@PathVariable String idNote) {
        return noteService.getNoteByIDNote(idNote)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matiere/{matiere}")
    public List<Note> getNotesByMatiere(@PathVariable String matiere) {
        return noteService.getNotesByMatiere(matiere);
    }

    @GetMapping("/date/{date}")
    public List<Note> getNotesByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return noteService.getNotesByDate(date);
    }

    @GetMapping("/eleve/{idEleve}")
    public List<Note> getNotesByEleve(@PathVariable String idEleve) {
        return noteService.getNotesByEleve(idEleve);
    }

    @GetMapping("/enseignant/{idEnseignant}")
    public List<Note> getNotesByEnseignant(@PathVariable String idEnseignant) {
        return noteService.getNotesByEnseignant(idEnseignant);
    }

    @GetMapping("/eleve-id/{eleveId}")
    public List<Note> getNotesByEleveId(@PathVariable UUID eleveId) {
        return noteService.getNotesByEleveId(eleveId);
    }

    @GetMapping("/enseignant-id/{enseignantId}")
    public List<Note> getNotesByEnseignantId(@PathVariable UUID enseignantId) {
        return noteService.getNotesByEnseignantId(enseignantId);
    }

    @GetMapping("/matiere/{matiere}/eleve/{idEleve}")
    public List<Note> getNotesByMatiereAndEleve(
            @PathVariable String matiere,
            @PathVariable String idEleve) {
        return noteService.getNotesByMatiereAndEleve(matiere, idEleve);
    }

    @PostMapping
    public Note ajouterNote(@RequestBody Note note) {
        return noteService.ajouterNote(note);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable UUID id, @RequestBody Note note) {
        return noteService.updateNote(id, note)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerNote(@PathVariable UUID id) {
        return noteService.supprimerNote(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
} 