package com.masar.services;

import com.masar.models.Rapport;
import com.masar.models.Utilisateur;
import com.masar.repositories.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RapportService {
    
    @Autowired
    private RapportRepository rapportRepository;

    public List<Rapport> getAllRapports() {
        return rapportRepository.findAll();
    }

    public Optional<Rapport> getRapportById(UUID id) {
        return rapportRepository.findById(id);
    }

    public Optional<Rapport> getRapportByIdRapport(String idRapport) {
        return rapportRepository.findByIdRapport(idRapport);
    }

    public List<Rapport> getRapportsByGenerePar(String generePar) {
        return rapportRepository.findByGenerePar(generePar);
    }

    public List<Rapport> getRapportsByDate(LocalDate date) {
        return rapportRepository.findByDate(date);
    }

    public List<Rapport> getRapportsByTypeRapport(String typeRapport) {
        return rapportRepository.findByTypeRapport(typeRapport);
    }

    public List<Rapport> getRapportsByUtilisateur(String utilisateurId) {
        return rapportRepository.findByUtilisateurId(utilisateurId);
    }

    public List<Rapport> getRapportsByPeriode(LocalDate dateDebut, LocalDate dateFin) {
        return rapportRepository.findByDateBetween(dateDebut, dateFin);
    }

    @Transactional
    public Rapport createRapport(Rapport rapport) {
        rapport.setDate(LocalDate.now()); // Set current date if not provided
        return rapportRepository.save(rapport);
    }

    @Transactional
    public Optional<Rapport> updateRapport(UUID id, Rapport rapport) {
        if (rapportRepository.existsById(id)) {
            rapport.setId(id);
            return Optional.of(rapportRepository.save(rapport));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean deleteRapport(UUID id) {
        if (rapportRepository.existsById(id)) {
            rapportRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Rapport genererRapport(String generePar, String typeRapport, String contenu, String utilisateurId) {
        Rapport rapport = new Rapport();
        rapport.setGenerePar(generePar);
        rapport.setTypeRapport(typeRapport);
        rapport.setContenu(contenu);
        rapport.setDate(LocalDate.now());
        rapport.setIdRapport("RAP" + System.currentTimeMillis()); // Generate unique ID

        // Set utilisateur if provided
        if (utilisateurId != null) {
            // You might want to validate the utilisateur exists here
            rapport.setUtilisateur(new Utilisateur()); // This should be fetched from repository
        }

        return createRapport(rapport);
    }
} 