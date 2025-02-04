package com.masar.services;

import com.masar.models.Parent;
import com.masar.models.Eleve;
import com.masar.models.Note;
import com.masar.models.Presence;
import com.masar.models.Ressource;
import com.masar.repositories.ParentRepository;
import com.masar.repositories.EleveRepository;
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
import java.util.stream.Collectors;

@Service
public class ParentService {
    
    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private EleveRepository eleveRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private RessourceRepository ressourceRepository;

    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    public Optional<Parent> getParentById(UUID id) {
        return parentRepository.findById(id);
    }

    public Optional<Parent> getParentByIdParent(String idParent) {
        return parentRepository.findByIdParent(idParent);
    }

    public List<Parent> getParentsByProfession(String profession) {
        return parentRepository.findByProfession(profession);
    }

    public List<Parent> getParentsByNombreEnfants(Integer nombreEnfants) {
        return parentRepository.findByNombreEnfants(nombreEnfants);
    }

    public List<Parent> getParentsByNombreEnfantsMin(Integer nombreEnfants) {
        return parentRepository.findByNombreEnfantsGreaterThanEqual(nombreEnfants);
    }

    public Parent createParent(Parent parent) {
        return parentRepository.save(parent);
    }

    public Optional<Parent> updateParent(UUID id, Parent parent) {
        if (parentRepository.existsById(id)) {
            parent.setId(id);
            return Optional.of(parentRepository.save(parent));
        }
        return Optional.empty();
    }

    public boolean deleteParent(UUID id) {
        if (parentRepository.existsById(id)) {
            parentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public void consulterNotes(UUID parentId) {
        Optional<Parent> parent = parentRepository.findById(parentId);
        if (parent.isPresent()) {
            // Here you would implement the logic to fetch children's grades
            // For example:
            // 1. Get list of children for this parent
            // 2. Fetch grades for each child
            // 3. Format and return the grades
        } else {
            throw new RuntimeException("Parent not found with id: " + parentId);
        }
    }

    @Transactional(readOnly = true)
    public void consulterPresence(UUID parentId) {
        Optional<Parent> parent = parentRepository.findById(parentId);
        if (parent.isPresent()) {
            // Here you would implement the logic to fetch children's attendance
            // For example:
            // 1. Get list of children for this parent
            // 2. Fetch attendance records for each child
            // 3. Calculate attendance statistics
            // 4. Format and return the attendance data
        } else {
            throw new RuntimeException("Parent not found with id: " + parentId);
        }
    }

    // Children information retrieval
    public List<Eleve> getEnfants(UUID parentId) {
        Optional<Parent> parent = parentRepository.findById(parentId);
        if (parent.isPresent()) {
            return eleveRepository.findByParent_Id(parentId);
        }
        throw new RuntimeException("Parent not found with id: " + parentId);
    }

    // Notes consultation
    public List<Note> consulterNotesEnfant(UUID parentId, UUID eleveId) {
        if (isParentOfEleve(parentId, eleveId)) {
            return noteRepository.findByEleveId(eleveId);
        }
        throw new RuntimeException("Not authorized to view this student's notes");
    }

    public List<Note> consulterNotesByMatiere(UUID parentId, UUID eleveId, String matiere) {
        if (isParentOfEleve(parentId, eleveId)) {
            Optional<Eleve> eleve = eleveRepository.findById(eleveId);
            if (eleve.isPresent()) {
                return noteRepository.findByMatiereAndIDEleve(matiere, eleve.get().getIdEleve());
            }
            throw new RuntimeException("Student not found");
        }
        throw new RuntimeException("Not authorized to view this student's notes");
    }

    // Presence consultation
    public List<Presence> consulterPresenceEnfant(UUID parentId, UUID eleveId) {
        if (isParentOfEleve(parentId, eleveId)) {
            return presenceRepository.findByEleveId(eleveId);
        }
        throw new RuntimeException("Not authorized to view this student's attendance");
    }

    public List<Presence> consulterPresenceParPeriode(UUID parentId, UUID eleveId, Date dateDebut, Date dateFin) {
        if (isParentOfEleve(parentId, eleveId)) {
            Optional<Eleve> eleve = eleveRepository.findById(eleveId);
            if (eleve.isPresent()) {
                return presenceRepository.findByIDEleveAndDateBetween(eleve.get().getIdEleve(), dateDebut, dateFin);
            }
            throw new RuntimeException("Student not found");
        }
        throw new RuntimeException("Not authorized to view this student's attendance");
    }

    // Resources consultation
    public List<Ressource> consulterRessourcesEnfant(UUID parentId, UUID eleveId) {
        if (isParentOfEleve(parentId, eleveId)) {
            Optional<Eleve> eleve = eleveRepository.findById(eleveId);
            if (eleve.isPresent()) {
                return ressourceRepository.findAll().stream()
                    .filter(r -> r.getNiveau().equals(eleve.get().getNiveau()))
                    .collect(Collectors.toList());
            }
            throw new RuntimeException("Student not found");
        }
        throw new RuntimeException("Not authorized to view this student's resources");
    }

    public List<Ressource> consulterRessourcesByMatiere(UUID parentId, UUID eleveId, String matiere) {
        if (isParentOfEleve(parentId, eleveId)) {
            Optional<Eleve> eleve = eleveRepository.findById(eleveId);
            if (eleve.isPresent()) {
                return ressourceRepository.findByTitre(matiere).stream()
                    .filter(r -> r.getNiveau().equals(eleve.get().getNiveau()))
                    .collect(Collectors.toList());
            }
            throw new RuntimeException("Student not found");
        }
        throw new RuntimeException("Not authorized to view this student's resources");
    }

    public List<Ressource> consulterRessourcesRecentes(UUID parentId, UUID eleveId, Date depuis) {
        if (isParentOfEleve(parentId, eleveId)) {
            Optional<Eleve> eleve = eleveRepository.findById(eleveId);
            if (eleve.isPresent()) {
                return ressourceRepository.findByDateTelechargeAfter(depuis).stream()
                    .filter(r -> r.getNiveau().equals(eleve.get().getNiveau()))
                    .collect(Collectors.toList());
            }
            throw new RuntimeException("Student not found");
        }
        throw new RuntimeException("Not authorized to view this student's resources");
    }

    // Helper method to check if a parent is authorized to view a student's information
    private boolean isParentOfEleve(UUID parentId, UUID eleveId) {
        Optional<Eleve> eleve = eleveRepository.findById(eleveId);
        if (eleve.isPresent()) {
            List<Eleve> enfants = eleveRepository.findByParent_Id(parentId);
            return enfants.stream().anyMatch(e -> e.getId().equals(eleveId));
        }
        return false;
    }
} 