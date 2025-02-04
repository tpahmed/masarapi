package com.masar.services;

import com.masar.models.Directeur;
import com.masar.models.Utilisateur;
import com.masar.models.Rapport;
import com.masar.repositories.DirecteurRepository;
import com.masar.repositories.UtilisateurRepository;
import com.masar.repositories.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DirecteurService {
    
    @Autowired
    private DirecteurRepository directeurRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private RapportService rapportService;

    public List<Directeur> getAllDirecteurs() {
        return directeurRepository.findAll();
    }

    public Optional<Directeur> getDirecteurById(UUID id) {
        return directeurRepository.findById(id);
    }

    public Directeur createDirecteur(Directeur directeur) {
        return directeurRepository.save(directeur);
    }

    public Optional<Directeur> updateDirecteur(UUID id, Directeur directeur) {
        if (directeurRepository.existsById(id)) {
            directeur.setId(id);
            return Optional.of(directeurRepository.save(directeur));
        }
        return Optional.empty();
    }

    public boolean deleteDirecteur(UUID id) {
        if (directeurRepository.existsById(id)) {
            directeurRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Utilisateur ajouterUtilisateur(UUID directeurId, Utilisateur utilisateur) {
        Optional<Directeur> directeur = directeurRepository.findById(directeurId);
        if (directeur.isPresent()) {
            // Generate report for user addition
            Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);
            rapportService.genererRapport(
                directeur.get().getNom(),
                "AJOUT_UTILISATEUR",
                "Ajout d'un nouvel utilisateur: " + utilisateur.getNom(),
                directeur.get().getId()
            );
            return savedUtilisateur;
        }
        throw new RuntimeException("Directeur not found with id: " + directeurId);
    }

    @Transactional
    public void approuverRessources(UUID directeurId) {
        Optional<Directeur> directeur = directeurRepository.findById(directeurId);
        if (directeur.isPresent()) {
            // Generate report for resource approval
            rapportService.genererRapport(
                directeur.get().getNom(),
                "APPROBATION_RESSOURCES",
                "Approbation des ressources par le directeur",
                directeur.get().getId()
            );
        } else {
            throw new RuntimeException("Directeur not found with id: " + directeurId);
        }
    }

    public List<Rapport> getRapportsByDirecteur(UUID directeurId) {
        Optional<Directeur> directeur = directeurRepository.findById(directeurId);
        if (directeur.isPresent()) {
            return rapportRepository.findByUtilisateurId(directeurId);
        }
        throw new RuntimeException("Directeur not found with id: " + directeurId);
    }

    public List<Rapport> getRapportsByDirecteurAndPeriode(UUID directeurId, LocalDate dateDebut, LocalDate dateFin) {
        Optional<Directeur> directeur = directeurRepository.findById(directeurId);
        if (directeur.isPresent()) {
            return rapportRepository.findByDateBetween(dateDebut, dateFin)
                .stream()
                .filter(rapport -> rapport.getUtilisateur().getId().equals(directeurId))
                .collect(Collectors.toList());
        }
        throw new RuntimeException("Directeur not found with id: " + directeurId);
    }
} 