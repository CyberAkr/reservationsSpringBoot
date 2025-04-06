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

import be.iccbxl.pid.reservationsspringboot.api.controller.hateoas.ArtistModelAssembler;
import be.iccbxl.pid.reservationsspringboot.model.Artist;
import be.iccbxl.pid.reservationsspringboot.repository.ArtistRepository;

// Ajoutez ces imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Artistes", description = "Opérations de gestion des artistes")
public class ArtistApiController {

    private final ArtistRepository repository;
    private final ArtistModelAssembler assembler;

    public ArtistApiController(ArtistRepository repository, ArtistModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    // GET all artists
    @Operation(summary = "Récupérer tous les artistes", 
               description = "Retourne la liste de tous les artistes avec des liens HATEOAS")
    @ApiResponse(responseCode = "200", 
                 description = "Liste des artistes récupérée avec succès", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CollectionModel.class)))
    @GetMapping("/artists")
    public CollectionModel<EntityModel<Artist>> all() {
        List<EntityModel<Artist>> artists = ((List<Artist>) repository.findAll()).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(artists,
                linkTo(methodOn(ArtistApiController.class).all()).withSelfRel());
    }

    // GET a single artist
    @Operation(summary = "Récupérer un artiste par son ID", 
               description = "Retourne les détails d'un artiste spécifique")
    @ApiResponse(responseCode = "200", 
                 description = "Artiste trouvé", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EntityModel.class)))
    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
    @GetMapping("/artists/{id}")
    public EntityModel<Artist> one(
        @Parameter(description = "ID de l'artiste à récupérer", required = true) 
        @PathVariable Long id
    ) {
        Artist artist = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        return assembler.toModel(artist);
    }

    // POST a new artist
    @Operation(summary = "Créer un nouvel artiste", 
               description = "Ajoute un nouvel artiste à la base de données")
    @ApiResponse(responseCode = "201", description = "Artiste créé avec succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @PostMapping("/admin/artists")
    public ResponseEntity<?> newArtist(
        @Parameter(description = "Détails du nouvel artiste", required = true)
        @RequestBody Artist newArtist
    ) {
        Artist savedArtist = repository.save(newArtist);

        return ResponseEntity
                .created(linkTo(methodOn(ArtistApiController.class).one(savedArtist.getId())).toUri())
                .body(assembler.toModel(savedArtist));
    }

    // PUT (update) an artist
    @Operation(summary = "Mettre à jour un artiste", 
               description = "Met à jour les informations d'un artiste existant")
    @ApiResponse(responseCode = "200", description = "Artiste mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
    @PutMapping("/admin/artists/{id}")
    public ResponseEntity<?> replaceArtist(
        @Parameter(description = "Nouvelles informations de l'artiste", required = true)
        @RequestBody Artist newArtist, 
        @Parameter(description = "ID de l'artiste à mettre à jour", required = true)
        @PathVariable Long id
    ) {
        Artist updatedArtist = repository.findById(id)
                .map(artist -> {
                    artist.setFirstname(newArtist.getFirstname());
                    artist.setLastname(newArtist.getLastname());
                    return repository.save(artist);
                })
                .orElseGet(() -> {
                    newArtist.setId(id);
                    return repository.save(newArtist);
                });

        return ResponseEntity
                .created(linkTo(methodOn(ArtistApiController.class).one(updatedArtist.getId())).toUri())
                .body(assembler.toModel(updatedArtist));
    }

    // DELETE an artist
    @Operation(summary = "Supprimer un artiste", 
               description = "Supprime un artiste de la base de données")
    @ApiResponse(responseCode = "204", description = "Artiste supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Artiste non trouvé")
    @DeleteMapping("/admin/artists/{id}")
    public ResponseEntity<?> deleteArtist(
        @Parameter(description = "ID de l'artiste à supprimer", required = true)
        @PathVariable Long id
    ) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}