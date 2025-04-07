package be.iccbxl.pid.reservationsspringboot.api.controller;

import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.repository.ShowRepository;
import be.iccbxl.pid.reservationsspringboot.api.controller.hateoas.ShowModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/shows")
public class ShowApiController {

    private static final Logger logger = LoggerFactory.getLogger(ShowApiController.class);

    @Autowired
    private ShowRepository repository;

    @Autowired
    private ShowModelAssembler assembler;

    @GetMapping("")
    public CollectionModel<EntityModel<Show>> all() {
        List<Show> shows = repository.findAll();
logger.info("Nombre de shows récupérés : {}", shows.size());
        logger.debug("Retrieved shows: {}", shows);

        List<EntityModel<Show>> showModels = shows.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(showModels,
                linkTo(methodOn(ShowApiController.class).all()).withRel("showList"));
    }
    @GetMapping("/{id}")
    public EntityModel<Show> one(@PathVariable("id") Long id) {
        Show show = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found with id " + id));

        return assembler.toModel(show);
    }
}