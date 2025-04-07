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

import be.iccbxl.pid.reservationsspringboot.api.controller.hateoas.ReservationModelAssembler;
import be.iccbxl.pid.reservationsspringboot.model.Reservation;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.repository.ReservationRepository;
import be.iccbxl.pid.reservationsspringboot.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Réservations", description = "Opérations de gestion des réservations")
public class ReservationApiController {

    private final ReservationRepository repository;
    private final UserRepository userRepository;
    private final ReservationModelAssembler assembler;

    public ReservationApiController(ReservationRepository repository, UserRepository userRepository, ReservationModelAssembler assembler) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.assembler = assembler;
    }

    // GET all reservations
    @Operation(summary = "Récupérer toutes les réservations", 
               description = "Retourne la liste de toutes les réservations avec des liens HATEOAS")
    @ApiResponse(responseCode = "200", 
                 description = "Liste des réservations récupérée avec succès", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CollectionModel.class)))
    @GetMapping("/admin/reservations")
    public CollectionModel<EntityModel<Reservation>> all() {
        List<EntityModel<Reservation>> reservations = ((List<Reservation>) repository.findAll()).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(reservations,
                linkTo(methodOn(ReservationApiController.class).all()).withSelfRel());
    }

    // GET a single reservation
    @Operation(summary = "Récupérer une réservation par son ID", 
               description = "Retourne les détails d'une réservation spécifique")
    @ApiResponse(responseCode = "200", 
                 description = "Réservation trouvée", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = EntityModel.class)))
    @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    @GetMapping("/reservations/{id}")
    public EntityModel<Reservation> one(
        @Parameter(description = "ID de la réservation à récupérer", required = true) 
        @PathVariable Long id
    ) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        return assembler.toModel(reservation);
    }
    
    // GET reservations for a user
    @Operation(summary = "Récupérer toutes les réservations d'un utilisateur", 
               description = "Retourne la liste des réservations pour un utilisateur spécifique")
    @ApiResponse(responseCode = "200", 
                 description = "Liste des réservations récupérée avec succès", 
                 content = @Content(mediaType = "application/json", 
                 schema = @Schema(implementation = CollectionModel.class)))
    @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    @GetMapping("/users/{userId}/reservations")
    public CollectionModel<EntityModel<Reservation>> getReservationsByUser(
        @Parameter(description = "ID de l'utilisateur", required = true) 
        @PathVariable Long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<EntityModel<Reservation>> reservations = repository.findByUser(user).stream()
                .map(assembler::toModel)
                .toList();

        return CollectionModel.of(reservations,
                linkTo(methodOn(ReservationApiController.class).getReservationsByUser(userId)).withSelfRel());
    }

    // POST a new reservation
    @Operation(summary = "Créer une nouvelle réservation", 
               description = "Ajoute une nouvelle réservation à la base de données")
    @ApiResponse(responseCode = "201", description = "Réservation créée avec succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide")
    @PostMapping("/reservations")
    public ResponseEntity<?> newReservation(
        @Parameter(description = "Détails de la nouvelle réservation", required = true)
        @RequestBody Reservation newReservation
    ) {
        Reservation savedReservation = repository.save(newReservation);

        return ResponseEntity
                .created(linkTo(methodOn(ReservationApiController.class).one(savedReservation.getId())).toUri())
                .body(assembler.toModel(savedReservation));
    }

    // PUT (update) a reservation
    @Operation(summary = "Mettre à jour une réservation", 
               description = "Met à jour les informations d'une réservation existante")
    @ApiResponse(responseCode = "200", description = "Réservation mise à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    @PutMapping("/reservations/{id}")
    public ResponseEntity<?> replaceReservation(
        @Parameter(description = "Nouvelles informations de la réservation", required = true)
        @RequestBody Reservation newReservation, 
        @Parameter(description = "ID de la réservation à mettre à jour", required = true)
        @PathVariable Long id
    ) {
        Reservation updatedReservation = repository.findById(id)
                .map(reservation -> {
                    reservation.setStatus(newReservation.getStatus());
                    // Mise à jour d'autres champs au besoin
                    return repository.save(reservation);
                })
                .orElseThrow(() -> new RuntimeException("Reservation not found with id " + id));
    
        return ResponseEntity
                .created(linkTo(methodOn(ReservationApiController.class).one(updatedReservation.getId())).toUri())
                .body(assembler.toModel(updatedReservation));
    }

    // DELETE a reservation
    @Operation(summary = "Supprimer une réservation", 
               description = "Supprime une réservation de la base de données")
    @ApiResponse(responseCode = "204", description = "Réservation supprimée avec succès")
    @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<?> deleteReservation(
        @Parameter(description = "ID de la réservation à supprimer", required = true)
        @PathVariable Long id
    ) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}