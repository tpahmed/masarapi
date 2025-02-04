package com.masar.repositories;

import com.masar.models.Directeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface DirecteurRepository extends JpaRepository<Directeur, UUID> {
    // Add custom queries if needed
} 