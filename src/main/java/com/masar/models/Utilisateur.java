package com.masar.models;

import lombok.Data;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "utilisateurs")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String motDePasse;
    
    @Column(nullable = false)
    private String role;
    
    private String numeroDeTelephone;
    private String adresse;

    public void seConnecter() {
        // Implementation will be handled in the service layer
    }

    public void seDeconnecter() {
        // Implementation will be handled in the service layer
    }
} 