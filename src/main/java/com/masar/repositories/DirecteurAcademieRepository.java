package com.masar.repositories;

import com.masar.models.DirecteurAcademie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DirecteurAcademieRepository extends JpaRepository<DirecteurAcademie, UUID> {
    List<DirecteurAcademie> findByRegion(String region);
    Optional<DirecteurAcademie> findByNomAcademie(String nomAcademie);
} 