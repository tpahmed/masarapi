package com.masar.repositories;

import com.masar.models.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RessourceRepository extends JpaRepository<Ressource, UUID> {
    Optional<Ressource> findByIDRessource(String idRessource);
    List<Ressource> findByTitre(String titre);
    List<Ressource> findByTelechargePar(String telechargePar);
    List<Ressource> findByDateTelecharge(Date dateTelecharge);
    List<Ressource> findByTypeFichier(String typeFichier);
    List<Ressource> findByEnseignantId(UUID enseignantId);
    List<Ressource> findByDateTelechargeAfter(Date date);
    List<Ressource> findByDateTelechargeBetween(Date dateDebut, Date dateFin);
} 