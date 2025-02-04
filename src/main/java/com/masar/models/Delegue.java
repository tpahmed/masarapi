package com.masar.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "delegues")
public class Delegue extends Utilisateur {
    
    @Column(nullable = false)
    private String region;

    @OneToMany(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private List<Rapport> rapports;

    public void genererRapports() {
        // Implementation will be added in service layer
    }
} 