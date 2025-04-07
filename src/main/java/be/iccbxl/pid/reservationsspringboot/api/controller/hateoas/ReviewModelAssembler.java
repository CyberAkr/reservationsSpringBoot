package be.iccbxl.pid.reservationsspringboot.api.controller.hateoas;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import be.iccbxl.pid.reservationsspringboot.api.controller.ReviewApiController;
import be.iccbxl.pid.reservationsspringboot.api.controller.ShowApiController;
import be.iccbxl.pid.reservationsspringboot.model.Review;

@Component
public class ReviewModelAssembler implements RepresentationModelAssembler<Review, EntityModel<Review>> {
    
    @Override
    public EntityModel<Review> toModel(Review review) {
        return EntityModel.of(review,
            linkTo(methodOn(ReviewApiController.class)
                .one(review.getId())).withSelfRel(),
            linkTo(methodOn(ReviewApiController.class).all())
                .withRel("reviews"),
            linkTo(methodOn(ShowApiController.class)
                .one(review.getShow().getId()))
                .withRel("show"));
    }
}