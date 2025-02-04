package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "eleves")
public class Eleve extends Utilisateur {
    
    @Column(name = "id_eleve", nullable = false, unique = true)
    private String idEleve;
    
    @Column(nullable = false)
    private String niveau;
    
    @Column(nullable = false)
    private Integer age;
    
    @Column(name = "annee_inscription", nullable = false)
    private LocalDate anneeInscription;
    
    @Column(nullable = false)
    private String classe;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    public void consulterNotes() {
        // Implementation will be added in service layer
    }

    public void consulterPresence() {
        // Implementation will be added in service layer
    }

    public void soumettreDevoir() {
        // Implementation will be added in service layer
    }

    public void accederRessources() {
        // Implementation will be added in service layer
    }
} 