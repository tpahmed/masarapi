package com.masar.services;

import com.masar.models.Ressource;
import com.masar.repositories.RessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RessourceService {
    
    @Autowired
    private RessourceRepository ressourceRepository;

    public List<Ressource> getAllRessources() {
        return ressourceRepository.findAll();
    }

    public Optional<Ressource> getRessourceById(UUID id) {
        return ressourceRepository.findById(id);
    }

    public Optional<Ressource> getRessourceByIDRessource(String idRessource) {
        return ressourceRepository.findByIDRessource(idRessource);
    }

    public List<Ressource> getRessourcesByTitre(String titre) {
        return ressourceRepository.findByTitre(titre);
    }

    public List<Ressource> getRessourcesByTelechargePar(String telechargePar) {
        return ressourceRepository.findByTelechargePar(telechargePar);
    }

    public List<Ressource> getRessourcesByDateTelecharge(Date dateTelecharge) {
        return ressourceRepository.findByDateTelecharge(dateTelecharge);
    }

    public List<Ressource> getRessourcesByTypeFichier(String typeFichier) {
        return ressourceRepository.findByTypeFichier(typeFichier);
    }

    public List<Ressource> getRessourcesByEnseignant(UUID enseignantId) {
        return ressourceRepository.findByEnseignantId(enseignantId);
    }

    public List<Ressource> getRessourcesRecentesDepuis(Date date) {
        return ressourceRepository.findByDateTelechargeAfter(date);
    }

    public List<Ressource> getRessourcesByPeriode(Date dateDebut, Date dateFin) {
        return ressourceRepository.findByDateTelechargeBetween(dateDebut, dateFin);
    }

    @Transactional
    public Ressource ajouterRessource(Ressource ressource) {
        ressource.setDateTelecharge(new Date()); // Set current date if not provided
        return ressourceRepository.save(ressource);
    }

    @Transactional
    public Optional<Ressource> modifierRessource(UUID id, Ressource ressource) {
        if (ressourceRepository.existsById(id)) {
            ressource.setId(id);
            return Optional.of(ressourceRepository.save(ressource));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean supprimerRessource(UUID id) {
        if (ressourceRepository.existsById(id)) {
            ressourceRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 