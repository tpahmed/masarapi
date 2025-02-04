package com.masar.repositories;

import com.masar.models.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, UUID> {
    Optional<Enseignant> findByIdEmploye(String idEmploye);
    List<Enseignant> findByMatiere(String matiere);
    List<Enseignant> findByQualification(String qualification);
    List<Enseignant> findByAnneesExperienceGreaterThanEqual(Integer anneesExperience);
    List<Enseignant> findByMatiereAndQualification(String matiere, String qualification);
} 