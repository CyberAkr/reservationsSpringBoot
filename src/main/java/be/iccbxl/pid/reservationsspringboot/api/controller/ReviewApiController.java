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

import be.iccbxl.pid.reservationsspringboot.api.controller.hateoas.ReviewModelAssembler;
import be.iccbxl.pid.reservationsspringboot.model.Review;
import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.repository.ReviewRepository;
import be.iccbxl.pid.reservationsspringboot.repository.ShowRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Avis", description = "Opérations de gestion des avis")
public class ReviewApiController {

    private final ReviewRepository repository;
    private final ShowRepository showRepository;
    private final ReviewModelAssembler assembler;

    public ReviewApiController(ReviewRepository repository, ShowRepository showRepository, ReviewModelAssembler assembler) {
        this.repository = repository;
        this.showRepository = showRepository;
        this.assembler = assembler;
    }

    // GET all reviews
    @Operation(summary = "Récupérer tous les avis", 
               description = "Retourne la liste de tous les avis avec des liens HATEOAS")
    @ApiResponse(responseCode = "200", 
                 description = "Liste des avis récupérée avec succès", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CollectionModel.class)))
    @GetMapping("/reviews")
    public CollectionModel<EntityModel<Review>> all() {
        List<EntityModel<Review>> reviews = ((List<Review>) repository.findAll()).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(reviews,
                linkTo(methodOn(ReviewApiController.class).all()).withSelfRel());
    }

    // GET a single review
    @Operation(summary = "Récupérer un avis par son ID", 
               description = "Retourne les détails d'un avis spécifique")
    @ApiResponse(responseCode = "200", 
                 description = "Avis trouvé", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EntityModel.class)))
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    @GetMapping("/reviews/{id}")
    public EntityModel<Review> one(
        @Parameter(description = "ID de l'avis à récupérer", required = true) 
        @PathVariable Long id
    ) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return assembler.toModel(review);
    }
    
    // GET reviews for a show
    @Operation(summary = "Récupérer tous les avis d'un spectacle", 
               description = "Retourne la liste des avis pour un spectacle spécifique")
    @ApiResponse(responseCode = "200", 
                 description = "Liste des avis récupérée avec succès", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CollectionModel.class)))
    @ApiResponse(responseCode = "404", description = "Spectacle non trouvé")
    @GetMapping("/shows/{showId}/reviews")
    public CollectionModel<EntityModel<Review>> getReviewsByShow(
        @Parameter(description = "ID du spectacle", required = true) 
        @PathVariable Long showId
    ) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));
        
        List<EntityModel<Review>> reviews = repository.findByShow(show).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(reviews,
                linkTo(methodOn(ReviewApiController.class).getReviewsByShow(showId)).withSelfRel(),
                linkTo(methodOn(ShowApiController.class).one(showId)).withRel("show"));
    }

    // POST a new review
    @Operation(summary = "Créer un nouvel avis", 
               description = "Ajoute un nouvel avis à la base de données")
    @ApiResponse(responseCode = "201", description = "Avis créé avec succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @PostMapping("/reviews")
    public ResponseEntity<?> newReview(
        @Parameter(description = "Détails du nouvel avis", required = true)
        @RequestBody Review newReview
    ) {
        Review savedReview = repository.save(newReview);

        return ResponseEntity
                .created(linkTo(methodOn(ReviewApiController.class).one(savedReview.getId())).toUri())
                .body(assembler.toModel(savedReview));
    }

    // PUT (update) a review
    @Operation(summary = "Mettre à jour un avis", 
               description = "Met à jour les informations d'un avis existant")
    @ApiResponse(responseCode = "200", description = "Avis mis à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    @PutMapping("/reviews/{id}")
    public ResponseEntity<?> replaceReview(
        @Parameter(description = "Nouvelles informations de l'avis", required = true)
        @RequestBody Review newReview, 
        @Parameter(description = "ID de l'avis à mettre à jour", required = true)
        @PathVariable Long id
    ) {
        Review updatedReview = repository.findById(id)
                .map(review -> {
                    review.setReview(newReview.getReview());
                    review.setStars(newReview.getStars());
                    review.setValidated(newReview.getValidated());
                    return repository.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found with id " + id));
    
        return ResponseEntity
                .created(linkTo(methodOn(ReviewApiController.class).one(updatedReview.getId())).toUri())
                .body(assembler.toModel(updatedReview));
    }//Validate a review
    @Operation(summary = "Valider un avis", 
               description = "Marque un avis comme validé")
    @ApiResponse(responseCode = "200", description = "Avis validé avec succès")
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    @PutMapping("/admin/reviews/{id}/validate")
    public ResponseEntity<?> validateReview(
        @Parameter(description = "ID de l'avis à valider", required = true)
        @PathVariable Long id
    ) {
        Review updatedReview = repository.findById(id)
                .map(review -> {
                    review.setValidated(true);
                    return repository.save(review);
                })
                .orElseThrow(() -> new RuntimeException("Review not found"));

        return ResponseEntity.ok(assembler.toModel(updatedReview));
    }

    // DELETE a review
    @Operation(summary = "Supprimer un avis", 
               description = "Supprime un avis de la base de données")
    @ApiResponse(responseCode = "204", description = "Avis supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Avis non trouvé")
    @DeleteMapping("/admin/reviews/{id}")
    public ResponseEntity<?> deleteReview(
        @Parameter(description = "ID de l'avis à supprimer", required = true)
        @PathVariable Long id
    ) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}