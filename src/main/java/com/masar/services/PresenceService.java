package com.masar.services;

import com.masar.models.Presence;
import com.masar.repositories.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PresenceService {
    
    @Autowired
    private PresenceRepository presenceRepository;

    public List<Presence> getAllPresences() {
        return presenceRepository.findAll();
    }

    public Optional<Presence> getPresenceById(UUID id) {
        return presenceRepository.findById(id);
    }

    public Optional<Presence> getPresenceByIDPresence(String idPresence) {
        return presenceRepository.findByIDPresence(idPresence);
    }

    public List<Presence> getPresencesByDate(Date date) {
        return presenceRepository.findByDate(date);
    }

    public List<Presence> getPresencesByStatut(String statut) {
        return presenceRepository.findByStatut(statut);
    }

    public List<Presence> getPresencesByEleve(String idEleve) {
        return presenceRepository.findByIDEleve(idEleve);
    }

    public List<Presence> getPresencesByEnseignant(String idEnseignant) {
        return presenceRepository.findByIDEnseignant(idEnseignant);
    }

    public List<Presence> getPresencesByEleveId(UUID eleveId) {
        return presenceRepository.findByEleveId(eleveId);
    }

    public List<Presence> getPresencesByEnseignantId(UUID enseignantId) {
        return presenceRepository.findByEnseignantId(enseignantId);
    }

    public List<Presence> getPresencesByPeriode(Date dateDebut, Date dateFin) {
        return presenceRepository.findByDateBetween(dateDebut, dateFin);
    }

    public List<Presence> getPresencesByEleveAndPeriode(String idEleve, Date dateDebut, Date dateFin) {
        return presenceRepository.findByIDEleveAndDateBetween(idEleve, dateDebut, dateFin);
    }

    @Transactional
    public Presence ajouterPresence(Presence presence) {
        presence.setDate(new Date()); // Set current date if not provided
        return presenceRepository.save(presence);
    }

    @Transactional
    public Optional<Presence> modifierPresence(UUID id, Presence presence) {
        if (presenceRepository.existsById(id)) {
            presence.setId(id);
            return Optional.of(presenceRepository.save(presence));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean supprimerPresence(UUID id) {
        if (presenceRepository.existsById(id)) {
            presenceRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 