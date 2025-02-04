package com.masar.repositories;

import com.masar.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<Note> findByIDNote(String idNote);
    List<Note> findByMatiere(String matiere);
    List<Note> findByDateAttribuee(Date dateAttribuee);
    List<Note> findByIDEleve(String idEleve);
    List<Note> findByIDEnseignant(String idEnseignant);
    List<Note> findByEleveId(UUID eleveId);
    List<Note> findByEnseignantId(UUID enseignantId);
    List<Note> findByMatiereAndIDEleve(String matiere, String idEleve);
} 