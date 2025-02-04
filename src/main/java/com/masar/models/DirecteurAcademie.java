package com.masar.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "directeurs_academie")
public class DirecteurAcademie extends Utilisateur {
    
    @Column(name = "nom_academie", nullable = false)
    private String nomAcademie;
    
    @Column(nullable = false)
    private String region;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Rapport> rapports;

    public void genererRapports() {
        // Implementation will be added in service layer
    }

    public void definirPolitiques() {
        // Implementation will be added in service layer
    }
} 