package be.iccbxl.pid.reservationsspringboot.api.controller.hateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import be.iccbxl.pid.reservationsspringboot.api.controller.ReservationApiController;
import be.iccbxl.pid.reservationsspringboot.model.Reservation;

@Component
public class ReservationModelAssembler implements RepresentationModelAssembler<Reservation, EntityModel<Reservation>> {
    
    @Override
    public EntityModel<Reservation> toModel(Reservation reservation) {
        return EntityModel.of(reservation,
            linkTo(methodOn(ReservationApiController.class)
                .one(reservation.getId())).withSelfRel(),
            linkTo(methodOn(ReservationApiController.class).all())
                .withRel("reservations"));
    }
}