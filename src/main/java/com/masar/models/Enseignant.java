package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("ENSEIGNANT")
public class Enseignant extends Utilisateur {
    
    @Column(nullable = false)
    private String matiere;
    
    @Column(name = "id_employe", nullable = false, unique = true)
    private String idEmploye;
    
    @Column(nullable = false)
    private String qualification;
    
    @Column(name = "annees_experience", nullable = false)
    private Integer anneesExperience;

    public void gererNotes() {
        // Implementation will be added in service layer
    }

    public void gererPresence() {
        // Implementation will be added in service layer
    }

    public void gererRessources() {
        // Implementation will be added in service layer
    }
} 