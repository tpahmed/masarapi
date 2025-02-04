package com.masar.services;

import com.masar.models.DirecteurAcademie;
import com.masar.models.Rapport;
import com.masar.repositories.DirecteurAcademieRepository;
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
public class DirecteurAcademieService {
    
    @Autowired
    private DirecteurAcademieRepository directeurAcademieRepository;

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private RapportService rapportService;

    public List<DirecteurAcademie> getAllDirecteursAcademie() {
        return directeurAcademieRepository.findAll();
    }

    public Optional<DirecteurAcademie> getDirecteurAcademieById(UUID id) {
        return directeurAcademieRepository.findById(id);
    }

    public List<DirecteurAcademie> getDirecteursAcademieByRegion(String region) {
        return directeurAcademieRepository.findByRegion(region);
    }

    public Optional<DirecteurAcademie> getDirecteurAcademieByNomAcademie(String nomAcademie) {
        return directeurAcademieRepository.findByNomAcademie(nomAcademie);
    }

    public DirecteurAcademie createDirecteurAcademie(DirecteurAcademie directeurAcademie) {
        return directeurAcademieRepository.save(directeurAcademie);
    }

    public Optional<DirecteurAcademie> updateDirecteurAcademie(UUID id, DirecteurAcademie directeurAcademie) {
        if (directeurAcademieRepository.existsById(id)) {
            directeurAcademie.setId(id);
            return Optional.of(directeurAcademieRepository.save(directeurAcademie));
        }
        return Optional.empty();
    }

    public boolean deleteDirecteurAcademie(UUID id) {
        if (directeurAcademieRepository.existsById(id)) {
            directeurAcademieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Rapport genererRapports(UUID directeurId, String typeRapport, String contenu) {
        Optional<DirecteurAcademie> directeur = directeurAcademieRepository.findById(directeurId);
        if (directeur.isPresent()) {
            return rapportService.genererRapport(
                directeur.get().getNom(),
                typeRapport,
                contenu,
                directeur.get().getId().toString()
            );
        }
        throw new RuntimeException("DirecteurAcademie not found with id: " + directeurId);
    }

    @Transactional
    public Rapport definirPolitiques(UUID directeurId, String politiques) {
        Optional<DirecteurAcademie> directeur = directeurAcademieRepository.findById(directeurId);
        if (directeur.isPresent()) {
            return rapportService.genererRapport(
                directeur.get().getNom(),
                "POLITIQUE_ACADEMIQUE",
                politiques,
                directeur.get().getId().toString()
            );
        }
        throw new RuntimeException("DirecteurAcademie not found with id: " + directeurId);
    }

    public List<Rapport> getRapportsByDirecteurAcademie(UUID directeurId) {
        Optional<DirecteurAcademie> directeur = directeurAcademieRepository.findById(directeurId);
        if (directeur.isPresent()) {
            return rapportRepository.findByUtilisateurId(directeurId.toString());
        }
        throw new RuntimeException("DirecteurAcademie not found with id: " + directeurId);
    }

    public List<Rapport> getRapportsByDirecteurAcademieAndPeriode(UUID directeurId, LocalDate dateDebut, LocalDate dateFin) {
        Optional<DirecteurAcademie> directeur = directeurAcademieRepository.findById(directeurId);
        if (directeur.isPresent()) {
            return rapportRepository.findByDateBetween(dateDebut, dateFin)
                .stream()
                .filter(rapport -> rapport.getUtilisateur().getId().equals(directeurId))
                .collect(Collectors.toList());
        }
        throw new RuntimeException("DirecteurAcademie not found with id: " + directeurId);
    }

    public List<Rapport> getRapportsByAcademie(String nomAcademie) {
        Optional<DirecteurAcademie> directeur = directeurAcademieRepository.findByNomAcademie(nomAcademie);
        if (directeur.isPresent()) {
            return rapportRepository.findByUtilisateurId(directeur.get().getId().toString());
        }
        throw new RuntimeException("DirecteurAcademie not found for academie: " + nomAcademie);
    }

    public List<Rapport> getRapportsByRegion(String region) {
        List<DirecteurAcademie> directeurs = directeurAcademieRepository.findByRegion(region);
        return directeurs.stream()
            .flatMap(directeur -> rapportRepository.findByUtilisateurId(directeur.getId().toString()).stream())
            .collect(Collectors.toList());
    }
} 