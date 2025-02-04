package com.masar.services;

import com.masar.models.Delegue;
import com.masar.models.Rapport;
import com.masar.repositories.DelegueRepository;
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
public class DelegueService {
    
    @Autowired
    private DelegueRepository delegueRepository;

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private RapportService rapportService;

    public List<Delegue> getAllDelegues() {
        return delegueRepository.findAll();
    }

    public Optional<Delegue> getDelegueById(UUID id) {
        return delegueRepository.findById(id);
    }

    public List<Delegue> getDeleguesByRegion(String region) {
        return delegueRepository.findByRegion(region);
    }

    public Delegue createDelegue(Delegue delegue) {
        return delegueRepository.save(delegue);
    }

    public Optional<Delegue> updateDelegue(UUID id, Delegue delegue) {
        if (delegueRepository.existsById(id)) {
            delegue.setId(id);
            return Optional.of(delegueRepository.save(delegue));
        }
        return Optional.empty();
    }

    public boolean deleteDelegue(UUID id) {
        if (delegueRepository.existsById(id)) {
            delegueRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Rapport genererRapports(UUID delegueId, String typeRapport, String contenu) {
        Optional<Delegue> delegue = delegueRepository.findById(delegueId);
        if (delegue.isPresent()) {
            return rapportService.genererRapport(
                delegue.get().getNom(),
                typeRapport,
                contenu,
                delegue.get().getId()
            );
        }
        throw new RuntimeException("Delegue not found with id: " + delegueId);
    }

    public List<Rapport> getRapportsByDelegue(UUID delegueId) {
        Optional<Delegue> delegue = delegueRepository.findById(delegueId);
        if (delegue.isPresent()) {
            return rapportRepository.findByUtilisateurId(delegueId);
        }
        throw new RuntimeException("Delegue not found with id: " + delegueId);
    }

    public List<Rapport> getRapportsByDelegueAndPeriode(UUID delegueId, LocalDate dateDebut, LocalDate dateFin) {
        Optional<Delegue> delegue = delegueRepository.findById(delegueId);
        if (delegue.isPresent()) {
            return rapportRepository.findByDateBetween(dateDebut, dateFin)
                .stream()
                .filter(rapport -> rapport.getUtilisateur().getId().equals(delegueId))
                .collect(Collectors.toList());
        }
        throw new RuntimeException("Delegue not found with id: " + delegueId);
    }

    public List<Rapport> getRapportsByRegion(String region) {
        List<Delegue> delegues = delegueRepository.findByRegion(region);
        return delegues.stream()
            .flatMap(delegue -> rapportRepository.findByUtilisateurId(delegue.getId()).stream())
            .collect(Collectors.toList());
    }
} 