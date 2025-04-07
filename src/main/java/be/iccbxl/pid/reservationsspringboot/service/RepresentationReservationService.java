package be.iccbxl.pid.reservationsspringboot.service;

import be.iccbxl.pid.reservationsspringboot.model.Representation;
import be.iccbxl.pid.reservationsspringboot.model.RepresentationReservation;
import be.iccbxl.pid.reservationsspringboot.model.Reservation;
import be.iccbxl.pid.reservationsspringboot.repository.RepresentationReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepresentationReservationService {
    @Autowired
    private RepresentationReservationRepository repository;
    
    public List<RepresentationReservation> getAll() {
        List<RepresentationReservation> representationReservations = new ArrayList<>();
        repository.findAll().forEach(representationReservations::add);
        
        return representationReservations;
    }
    
    public Optional<RepresentationReservation> get(Long id) {
        return repository.findById(id);
    }
    
    public List<RepresentationReservation> getByReservation(Reservation reservation) {
        return repository.findByReservation(reservation);
    }
    
    public List<RepresentationReservation> getByRepresentation(Representation representation) {
        return repository.findByRepresentation(representation);
    }
    
    public void add(RepresentationReservation representationReservation) {
        repository.save(representationReservation);
    }
    
    public void update(Long id, RepresentationReservation representationReservation) {
        repository.save(representationReservation);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}