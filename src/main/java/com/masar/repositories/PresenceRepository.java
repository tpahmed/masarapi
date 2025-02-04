package com.masar.repositories;

import com.masar.models.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, UUID> {
    Optional<Presence> findByIDPresence(String idPresence);
    List<Presence> findByDate(Date date);
    List<Presence> findByStatut(String statut);
    List<Presence> findByIDEleve(String idEleve);
    List<Presence> findByIDEnseignant(String idEnseignant);
    List<Presence> findByEleveId(UUID eleveId);
    List<Presence> findByEnseignantId(UUID enseignantId);
    List<Presence> findByDateBetween(Date dateDebut, Date dateFin);
    List<Presence> findByIDEleveAndDateBetween(String idEleve, Date dateDebut, Date dateFin);
} 