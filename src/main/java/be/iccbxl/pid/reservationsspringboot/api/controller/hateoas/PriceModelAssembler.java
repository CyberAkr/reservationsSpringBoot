package be.iccbxl.pid.reservationsspringboot.api.controller.hateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import be.iccbxl.pid.reservationsspringboot.api.controller.PriceApiController;
import be.iccbxl.pid.reservationsspringboot.model.Price;

@Component
public class PriceModelAssembler implements RepresentationModelAssembler<Price, EntityModel<Price>> {
    
    @Override
    public EntityModel<Price> toModel(Price price) {
        return EntityModel.of(price,
            linkTo(methodOn(PriceApiController.class)
                .one(price.getId())).withSelfRel(),
            linkTo(methodOn(PriceApiController.class).all())
                .withRel("prices"));
    }
}