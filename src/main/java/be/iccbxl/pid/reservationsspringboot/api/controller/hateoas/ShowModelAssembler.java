package be.iccbxl.pid.reservationsspringboot.api.controller.hateoas;

import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.api.controller.ShowApiController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ShowModelAssembler implements RepresentationModelAssembler<Show, EntityModel<Show>> {
    @Override
    public EntityModel<Show> toModel(Show show) {
        return EntityModel.of(show,
            linkTo(methodOn(ShowApiController.class).one(show.getId())).withSelfRel(),
            linkTo(methodOn(ShowApiController.class).all()).withRel("showList")
        );
    }
}