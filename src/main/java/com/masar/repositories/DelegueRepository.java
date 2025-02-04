package com.masar.repositories;

import com.masar.models.Delegue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface DelegueRepository extends JpaRepository<Delegue, UUID> {
    List<Delegue> findByRegion(String region);
} 