package com.masar.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PARENT")
@NoArgsConstructor
@AllArgsConstructor
public class Parent extends Utilisateur {
    
    @Column(name = "id_parent", nullable = false, unique = true)
    private String idParent;
    
    @Column(nullable = false)
    private String profession;
    
    @Column(name = "nombre_enfants", nullable = false)
    private Integer nombreEnfants;

    public void consulterNotes() {
        // Implementation will be added in service layer
    }

    public void consulterPresence() {
        // Implementation will be added in service layer
    }
} 