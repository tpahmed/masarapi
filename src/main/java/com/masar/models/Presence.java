package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@DiscriminatorValue("PRESENCE")
public class Presence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "id_presence", nullable = false)
    private String IDPresence;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;
    
    @Column(nullable = false)
    private String statut;
    
    @Column(name = "id_eleve", nullable = false)
    private String IDEleve;
    
    @Column(name = "id_enseignant", nullable = false)
    private String IDEnseignant;
    
    @Column(nullable = false)
    private String remarques;

    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleve eleve;

    @ManyToOne
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    public void ajouterPresence() {
        // Implementation will be added in service layer
    }

    public void modifierPresence() {
        // Implementation will be added in service layer
    }

    public void supprimerPresence() {
        // Implementation will be added in service layer
    }
} 