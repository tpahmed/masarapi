package com.masar.repositories;

import com.masar.models.Delegue;
import com.masar.models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, UUID> {
    Optional<Rapport> findByIdRapport(String idRapport);
    List<Rapport> findByDelegue(Delegue delegue);
    List<Rapport> findByDelegueAndDateCreationBetween(Delegue delegue, LocalDateTime dateDebut, LocalDateTime dateFin);
    List<Rapport> findByDelegueRegion(String region);
    List<Rapport> findByGenerePar(String generePar);
    List<Rapport> findByDate(LocalDate date);
    List<Rapport> findByTypeRapport(String typeRapport);
    List<Rapport> findByUtilisateurId(String utilisateurId);
    List<Rapport> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);
} 
