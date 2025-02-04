package com.masar.repositories;

import com.masar.models.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParentRepository extends JpaRepository<Parent, UUID> {
    Optional<Parent> findByIdParent(String idParent);
    List<Parent> findByProfession(String profession);
    List<Parent> findByNombreEnfants(Integer nombreEnfants);
    List<Parent> findByNombreEnfantsGreaterThanEqual(Integer nombreEnfants);
} 