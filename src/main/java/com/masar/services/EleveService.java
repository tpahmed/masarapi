package com.masar.services;

import com.masar.models.Eleve;
import com.masar.models.Note;
import com.masar.models.Presence;
import com.masar.models.Ressource;
import com.masar.repositories.EleveRepository;
import com.masar.repositories.NoteRepository;
import com.masar.repositories.PresenceRepository;
import com.masar.repositories.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EleveService {
    
    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Optional<Eleve> getEleveById(UUID id) {
        return eleveRepository.findById(id);
    }

    public Optional<Eleve> getEleveByIdEleve(String idEleve) {
        return eleveRepository.findByIdEleve(idEleve);
    }

    public List<Eleve> getElevesByNiveau(String niveau) {
        return eleveRepository.findByNiveau(niveau);
    }

    public List<Eleve> getElevesByClasse(String classe) {
        return eleveRepository.findByClasse(classe);
    }

    public List<Eleve> getElevesByAnneeInscription(LocalDate anneeInscription) {
        return eleveRepository.findByAnneeInscription(anneeInscription);
    }

    public List<Eleve> getElevesByClasseAndNiveau(String classe, String niveau) {
        return eleveRepository.findByClasseAndNiveau(classe, niveau);
    }

    public Eleve createEleve(Eleve eleve) {
        return eleveRepository.save(eleve);
    }

    public Optional<Eleve> updateEleve(UUID id, Eleve eleve) {
        if (eleveRepository.existsById(id)) {
            eleve.setId(id);
            return Optional.of(eleveRepository.save(eleve));
        }
        return Optional.empty();
    }

    public boolean deleteEleve(UUID id) {
        if (eleveRepository.existsById(id)) {
            eleveRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Note-related methods
    public List<Note> consulterNotes(UUID eleveId) {
        return noteRepository.findByEleveId(eleveId);
    }

    public List<Note> consulterNotesByMatiere(UUID eleveId, String matiere) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            return noteRepository.findByMatiereAndIDEleve(matiere, eleve.get().getIdEleve());
        }
        throw new RuntimeException("Eleve not found with id: " + eleveId);
    }

    public List<Ressource> accederRessources(UUID eleveId) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            // Return all available resources
            return ressourceRepository.findAll();
        }
        throw new RuntimeException("Eleve not found with id: " + eleveId);
    }

    public List<Ressource> accederRessourcesByMatiere(UUID eleveId, String matiere) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            // Return resources filtered by subject
            return ressourceRepository.findByTitre(matiere);
        }
        throw new RuntimeException("Eleve not found with id: " + eleveId);
    }

    public List<Ressource> accederRessourcesRecentes(UUID eleveId, Date depuis) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            // Return recent resources
            return ressourceRepository.findByDateTelechargeAfter(depuis);
        }
        throw new RuntimeException("Eleve not found with id: " + eleveId);
    }

    public List<Presence> consulterPresence(UUID eleveId) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            return presenceRepository.findByEleveId(eleveId);
        }
        throw new RuntimeException("Eleve not found with id: " + eleveId);
    }

    public List<Presence> consulterPresenceParPeriode(UUID eleveId, Date dateDebut, Date dateFin) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            return presenceRepository.findByIDEleveAndDateBetween(eleve.get().getIdEleve(), dateDebut, dateFin);
        }
        throw new RuntimeException("Eleve not found with id: " + eleveId);
    }
} 