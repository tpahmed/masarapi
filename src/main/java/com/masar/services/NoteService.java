package com.masar.services;

import com.masar.models.Note;
import com.masar.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Optional<Note> getNoteById(UUID id) {
        return noteRepository.findById(id);
    }

    public Optional<Note> getNoteByIDNote(String idNote) {
        return noteRepository.findByIDNote(idNote);
    }

    public List<Note> getNotesByMatiere(String matiere) {
        return noteRepository.findByMatiere(matiere);
    }

    public List<Note> getNotesByDate(Date dateAttribuee) {
        return noteRepository.findByDateAttribuee(dateAttribuee);
    }

    public List<Note> getNotesByEleve(String idEleve) {
        return noteRepository.findByIDEleve(idEleve);
    }

    public List<Note> getNotesByEnseignant(String idEnseignant) {
        return noteRepository.findByIDEnseignant(idEnseignant);
    }

    public List<Note> getNotesByEleveId(UUID eleveId) {
        return noteRepository.findByEleveId(eleveId);
    }

    public List<Note> getNotesByEnseignantId(UUID enseignantId) {
        return noteRepository.findByEnseignantId(enseignantId);
    }

    public List<Note> getNotesByMatiereAndEleve(String matiere, String idEleve) {
        return noteRepository.findByMatiereAndIDEleve(matiere, idEleve);
    }

    @Transactional
    public Note ajouterNote(Note note) {
        note.setDateAttribuee(new Date()); // Set current date if not provided
        return noteRepository.save(note);
    }

    @Transactional
    public Optional<Note> updateNote(UUID id, Note note) {
        if (noteRepository.existsById(id)) {
            note.setId(id);
            return Optional.of(noteRepository.save(note));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean supprimerNote(UUID id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 