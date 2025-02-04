package com.masar.repositories;

import com.masar.models.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EleveRepository extends JpaRepository<Eleve, UUID> {
    Optional<Eleve> findByIdEleve(String idEleve);
    List<Eleve> findByNiveau(String niveau);
    List<Eleve> findByClasse(String classe);
    List<Eleve> findByAnneeInscription(LocalDate anneeInscription);
    List<Eleve> findByClasseAndNiveau(String classe, String niveau);
    List<Eleve> findByParent_Id(UUID parentId);
} 