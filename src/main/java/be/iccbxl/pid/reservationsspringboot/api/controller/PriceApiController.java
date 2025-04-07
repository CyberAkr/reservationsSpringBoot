package be.iccbxl.pid.reservationsspringboot.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.iccbxl.pid.reservationsspringboot.api.controller.hateoas.PriceModelAssembler;
import be.iccbxl.pid.reservationsspringboot.model.Price;
import be.iccbxl.pid.reservationsspringboot.repository.PriceRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Prix", description = "Opérations de gestion des prix")
public class PriceApiController {

    private final PriceRepository repository;
    private final PriceModelAssembler assembler;

    public PriceApiController(PriceRepository repository, PriceModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // GET all prices
    @Operation(summary = "Récupérer tous les prix", 
               description = "Retourne la liste de tous les prix avec des liens HATEOAS")
    @ApiResponse(responseCode = "200", 
                 description = "Liste des prix récupérée avec succès", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CollectionModel.class)))
    @GetMapping("/prices")
    public CollectionModel<EntityModel<Price>> all() {
        List<EntityModel<Price>> prices = ((List<Price>) repository.findAll()).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(prices,
                linkTo(methodOn(PriceApiController.class).all()).withSelfRel());
    }

    // GET a single price
    @Operation(summary = "Récupérer un prix par son ID", 
               description = "Retourne les détails d'un prix spécifique")
    @ApiResponse(responseCode = "200", 
                 description = "Prix trouvé", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EntityModel.class)))
    @ApiResponse(responseCode = "404", description = "Prix non trouvé")
    @GetMapping("/prices/{id}")
    public EntityModel<Price> one(
        @Parameter(description = "ID du prix à récupérer", required = true) 
        @PathVariable Long id
    ) {
        Price price = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Price not found"));

        return assembler.toModel(price);
    }

    // POST a new price
    @Operation(summary = "Créer un nouveau prix", 
               description = "Ajoute un nouveau prix à la base de données")
    @ApiResponse(responseCode = "201", description = "Prix créé avec succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @PostMapping("/admin/prices")
    public ResponseEntity<?> newPrice(
        @Parameter(description = "Détails du nouveau prix", required = true)
        @RequestBody Price newPrice
    ) {
        Price savedPrice = repository.save(newPrice);

        return ResponseEntity
                .created(linkTo(methodOn(PriceApiController.class).one(savedPrice.getId())).toUri())
                .body(assembler.toModel(savedPrice));
    }

    // PUT (update) a price
    @Operation(summary = "Mettre à jour un prix", 
               description = "Met à jour les informations d'un prix existant")
    @ApiResponse(responseCode = "200", description = "Prix mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Prix non trouvé")
    @PutMapping("/admin/prices/{id}")
    public ResponseEntity<?> replacePrice(
        @Parameter(description = "Nouvelles informations du prix", required = true)
        @RequestBody Price newPrice, 
        @Parameter(description = "ID du prix à mettre à jour", required = true)
        @PathVariable Long id
    ) {
        Price updatedPrice = repository.findById(id)
                .map(price -> {
                    price.setType(newPrice.getType());
                    price.setPrice(newPrice.getPrice());
                    price.setStart_date(newPrice.getStart_date());
                    price.setEnd_date(newPrice.getEnd_date());
                    return repository.save(price);
                })
                .orElseThrow(() -> new RuntimeException("Price not found with id " + id));
    
        return ResponseEntity
                .created(linkTo(methodOn(PriceApiController.class).one(updatedPrice.getId())).toUri())
                .body(assembler.toModel(updatedPrice));
    }

    // DELETE a price
    @Operation(summary = "Supprimer un prix", 
               description = "Supprime un prix de la base de données")
    @ApiResponse(responseCode = "204", description = "Prix supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Prix non trouvé")
    @DeleteMapping("/admin/prices/{id}")
    public ResponseEntity<?> deletePrice(
        @Parameter(description = "ID du prix à supprimer", required = true)
        @PathVariable Long id
    ) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}