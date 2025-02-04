package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@DiscriminatorValue("RAPPORT")
public class Rapport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "id_rapport", nullable = false, unique = true)
    private String idRapport;
    
    @Column(name = "genere_par", nullable = false)
    private String generePar;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contenu;
    
    @Column(name = "type_rapport", nullable = false)
    private String typeRapport;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public void genererRapport() {
        // Implementation will be added in service layer
    }
} 