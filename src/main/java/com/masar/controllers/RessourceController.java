package com.masar.controllers;

import com.masar.models.Ressource;
import com.masar.services.RessourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ressources")
public class RessourceController {

    @Autowired
    private RessourceService ressourceService;

    @GetMapping
    public List<Ressource> getAllRessources() {
        return ressourceService.getAllRessources();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ressource> getRessourceById(@PathVariable UUID id) {
        return ressourceService.getRessourceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/id-ressource/{idRessource}")
    public ResponseEntity<Ressource> getRessourceByIDRessource(@PathVariable String idRessource) {
        return ressourceService.getRessourceByIDRessource(idRessource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titre/{titre}")
    public List<Ressource> getRessourcesByTitre(@PathVariable String titre) {
        return ressourceService.getRessourcesByTitre(titre);
    }

    @GetMapping("/telecharge-par/{telechargePar}")
    public List<Ressource> getRessourcesByTelechargePar(@PathVariable String telechargePar) {
        return ressourceService.getRessourcesByTelechargePar(telechargePar);
    }

    @GetMapping("/date/{date}")
    public List<Ressource> getRessourcesByDateTelecharge(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ressourceService.getRessourcesByDateTelecharge(date);
    }

    @GetMapping("/type-fichier/{typeFichier}")
    public List<Ressource> getRessourcesByTypeFichier(@PathVariable String typeFichier) {
        return ressourceService.getRessourcesByTypeFichier(typeFichier);
    }

    @GetMapping("/enseignant/{enseignantId}")
    public List<Ressource> getRessourcesByEnseignant(@PathVariable UUID enseignantId) {
        return ressourceService.getRessourcesByEnseignant(enseignantId);
    }

    @GetMapping("/recentes")
    public List<Ressource> getRessourcesRecentesDepuis(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ressourceService.getRessourcesRecentesDepuis(date);
    }

    @GetMapping("/periode")
    public List<Ressource> getRessourcesByPeriode(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin) {
        return ressourceService.getRessourcesByPeriode(dateDebut, dateFin);
    }

    @PostMapping
    public Ressource ajouterRessource(@RequestBody Ressource ressource) {
        return ressourceService.ajouterRessource(ressource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ressource> modifierRessource(
            @PathVariable UUID id,
            @RequestBody Ressource ressource) {
        return ressourceService.modifierRessource(id, ressource)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerRessource(@PathVariable UUID id) {
        return ressourceService.supprimerRessource(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
} 