package com.masar.repositories;

import com.masar.models.Delegue;
import com.masar.models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, UUID> {
    Optional<Rapport> findByIdRapport(String idRapport);
    
    @Query("SELECT r FROM Rapport r WHERE r.utilisateur = :delegue")
    List<Rapport> findByDelegue(@Param("delegue") Delegue delegue);
    
    @Query("SELECT r FROM Rapport r WHERE r.utilisateur = :delegue AND r.date BETWEEN :dateDebut AND :dateFin")
    List<Rapport> findByDelegueAndDateCreationBetween(
        @Param("delegue") Delegue delegue, 
        @Param("dateDebut") LocalDateTime dateDebut, 
        @Param("dateFin") LocalDateTime dateFin
    );
    
    @Query("SELECT r FROM Rapport r JOIN r.utilisateur u WHERE TYPE(u) = Delegue AND TREAT(u AS Delegue).region = :region")
    List<Rapport> findByDelegueRegion(@Param("region") String region);
    
    List<Rapport> findByGenerePar(String generePar);
    List<Rapport> findByDate(LocalDate date);
    List<Rapport> findByTypeRapport(String typeRapport);
    List<Rapport> findByUtilisateurId(UUID utilisateurId);
    List<Rapport> findByDateBetween(LocalDate dateDebut, LocalDate dateFin);
} 
