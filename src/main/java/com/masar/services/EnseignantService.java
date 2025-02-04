package com.masar.services;

import com.masar.models.Enseignant;
import com.masar.models.Note;
import com.masar.models.Presence;
import com.masar.models.Ressource;
import com.masar.repositories.EnseignantRepository;
import com.masar.repositories.NoteRepository;
import com.masar.repositories.PresenceRepository;
import com.masar.repositories.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EnseignantService {
    
    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    @Autowired
    private NoteService noteService;

    @Autowired
    private PresenceService presenceService;

    @Autowired
    private RessourceService ressourceService;

    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public Optional<Enseignant> getEnseignantById(UUID id) {
        return enseignantRepository.findById(id);
    }

    public Optional<Enseignant> getEnseignantByIdEmploye(String idEmploye) {
        return enseignantRepository.findByIdEmploye(idEmploye);
    }

    public List<Enseignant> getEnseignantsByMatiere(String matiere) {
        return enseignantRepository.findByMatiere(matiere);
    }

    public List<Enseignant> getEnseignantsByQualification(String qualification) {
        return enseignantRepository.findByQualification(qualification);
    }

    public List<Enseignant> getEnseignantsByExperienceMin(Integer anneesExperience) {
        return enseignantRepository.findByAnneesExperienceGreaterThanEqual(anneesExperience);
    }

    public List<Enseignant> getEnseignantsByMatiereAndQualification(String matiere, String qualification) {
        return enseignantRepository.findByMatiereAndQualification(matiere, qualification);
    }

    public Enseignant createEnseignant(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }

    public Optional<Enseignant> updateEnseignant(UUID id, Enseignant enseignant) {
        if (enseignantRepository.existsById(id)) {
            enseignant.setId(id);
            return Optional.of(enseignantRepository.save(enseignant));
        }
        return Optional.empty();
    }

    public boolean deleteEnseignant(UUID id) {
        if (enseignantRepository.existsById(id)) {
            enseignantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Note CRUD operations
    @Transactional
    public Note createNote(UUID enseignantId, Note note) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            note.setEnseignant(enseignant.get());
            note.setIDEnseignant(enseignant.get().getId().toString());
            note.setDateAttribuee(new Date());
            return noteService.ajouterNote(note);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    public List<Note> getAllNotes(UUID enseignantId) {
        return noteRepository.findByEnseignantId(enseignantId);
    }

    public Optional<Note> getNoteById(UUID enseignantId, UUID noteId) {
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent() && note.get().getEnseignant().getId().equals(enseignantId)) {
            return note;
        }
        return Optional.empty();
    }

    public List<Note> getNotesByMatiere(UUID enseignantId, String matiere) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            return noteRepository.findByMatiere(matiere);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    public List<Note> getNotesByEleve(UUID enseignantId, String idEleve) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            return noteRepository.findByIDEleve(idEleve);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    @Transactional
    public Optional<Note> updateNote(UUID enseignantId, UUID noteId, Note note) {
        Optional<Note> existingNote = noteRepository.findById(noteId);
        if (existingNote.isPresent() && existingNote.get().getEnseignant().getId().equals(enseignantId)) {
            note.setId(noteId);
            note.setEnseignant(existingNote.get().getEnseignant());
            note.setIDEnseignant(enseignantId.toString());
            return Optional.of(noteRepository.save(note));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteNote(UUID enseignantId, UUID noteId) {
        Optional<Note> note = noteRepository.findById(noteId);
        if (note.isPresent() && note.get().getEnseignant().getId().equals(enseignantId)) {
            noteRepository.deleteById(noteId);
            return true;
        }
        return false;
    }

    // Presence CRUD operations
    @Transactional
    public Presence createPresence(UUID enseignantId, Presence presence) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            presence.setEnseignant(enseignant.get());
            presence.setIDEnseignant(enseignant.get().getId().toString());
            presence.setDate(new Date());
            return presenceService.ajouterPresence(presence);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    public List<Presence> getAllPresences(UUID enseignantId) {
        return presenceRepository.findByEnseignantId(enseignantId);
    }

    public Optional<Presence> getPresenceById(UUID enseignantId, UUID presenceId) {
        Optional<Presence> presence = presenceRepository.findById(presenceId);
        if (presence.isPresent() && presence.get().getEnseignant().getId().equals(enseignantId)) {
            return presence;
        }
        return Optional.empty();
    }

    public List<Presence> getPresencesByEleve(UUID enseignantId, String idEleve) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            return presenceRepository.findByIDEleve(idEleve);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    public List<Presence> getPresencesByPeriode(UUID enseignantId, Date dateDebut, Date dateFin) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            return presenceRepository.findByDateBetween(dateDebut, dateFin);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    @Transactional
    public Optional<Presence> updatePresence(UUID enseignantId, UUID presenceId, Presence presence) {
        Optional<Presence> existingPresence = presenceRepository.findById(presenceId);
        if (existingPresence.isPresent() && existingPresence.get().getEnseignant().getId().equals(enseignantId)) {
            presence.setId(presenceId);
            presence.setEnseignant(existingPresence.get().getEnseignant());
            presence.setIDEnseignant(enseignantId.toString());
            return Optional.of(presenceRepository.save(presence));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deletePresence(UUID enseignantId, UUID presenceId) {
        Optional<Presence> presence = presenceRepository.findById(presenceId);
        if (presence.isPresent() && presence.get().getEnseignant().getId().equals(enseignantId)) {
            presenceRepository.deleteById(presenceId);
            return true;
        }
        return false;
    }

    // Resource CRUD operations
    @Transactional
    public Ressource createRessource(UUID enseignantId, Ressource ressource) {
        Optional<Enseignant> enseignant = enseignantRepository.findById(enseignantId);
        if (enseignant.isPresent()) {
            ressource.setEnseignant(enseignant.get());
            ressource.setTelechargePar(enseignant.get().getNom());
            ressource.setDateTelecharge(new Date());
            return ressourceService.ajouterRessource(ressource);
        }
        throw new RuntimeException("Enseignant not found with id: " + enseignantId);
    }

    public List<Ressource> getAllRessources(UUID enseignantId) {
        return ressourceRepository.findByEnseignantId(enseignantId);
    }

    public Optional<Ressource> getRessourceById(UUID enseignantId, UUID ressourceId) {
        Optional<Ressource> ressource = ressourceRepository.findById(ressourceId);
        if (ressource.isPresent() && ressource.get().getEnseignant().getId().equals(enseignantId)) {
            return ressource;
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Ressource> updateRessource(UUID enseignantId, UUID ressourceId, Ressource ressource) {
        Optional<Ressource> existingRessource = ressourceRepository.findById(ressourceId);
        if (existingRessource.isPresent() && existingRessource.get().getEnseignant().getId().equals(enseignantId)) {
            ressource.setId(ressourceId);
            ressource.setEnseignant(existingRessource.get().getEnseignant());
            ressource.setTelechargePar(existingRessource.get().getTelechargePar());
            return Optional.of(ressourceRepository.save(ressource));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteRessource(UUID enseignantId, UUID ressourceId) {
        Optional<Ressource> ressource = ressourceRepository.findById(ressourceId);
        if (ressource.isPresent() && ressource.get().getEnseignant().getId().equals(enseignantId)) {
            ressourceRepository.deleteById(ressourceId);
            return true;
        }
        return false;
    }
} 