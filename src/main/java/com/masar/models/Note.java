package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("NOTE")
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "id_note", nullable = false)
    private String IDNote;
    
    @Column(nullable = false)
    private String matiere;
    
    @Column(nullable = false)
    private Float note;
    
    @Column(name = "date_attribuee", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateAttribuee;
    
    @Column(name = "id_eleve", nullable = false)
    private String IDEleve;
    
    @Column(name = "id_enseignant", nullable = false)
    private String IDEnseignant;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    public void ajouterNote() {
        // Implementation will be added in service layer
    }

    public void supprimerNote() {
        // Implementation will be added in service layer
    }
} 