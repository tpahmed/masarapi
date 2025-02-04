package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@DiscriminatorValue("RESSOURCE")
public class Ressource {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "id_ressource", nullable = false)
    private String IDRessource;
    
    @Column(nullable = false)
    private String titre;
    
    @Column(nullable = false)
    private String niveau;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "telecharge_par", nullable = false)
    private String telechargePar;
    
    @Column(name = "date_telechargement")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTelecharge;
    
    @Column(name = "type_fichier")
    private String typeFichier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enseignant_id")
    private Enseignant enseignant;

    @PrePersist
    @PreUpdate
    private void updateTelechargePar() {
        if (enseignant != null) {
            this.telechargePar = enseignant.getId().toString();
        }
    }

    public void ajouterRessource() {
        // Implementation will be added in service layer
    }

    public void modifierRessource() {
        // Implementation will be added in service layer
    }

    public void supprimerRessource() {
        // Implementation will be added in service layer
    }
} 