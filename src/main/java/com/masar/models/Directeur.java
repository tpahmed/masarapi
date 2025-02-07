package com.masar.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("DIRECTEUR")
public class Directeur extends Utilisateur {
    
    @Column(name = "id_bureau")
    private String idBureau;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Rapport> rapports;

    public void ajouterUtilisateur(Utilisateur utilisateur) {
        // Implementation will be added in service layer
    }

    public void approuverRessources() {
        // Implementation will be added in service layer
    }
} 