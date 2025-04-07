package be.iccbxl.pid.reservationsspringboot.repository;

import be.iccbxl.pid.reservationsspringboot.model.Representation;
import be.iccbxl.pid.reservationsspringboot.model.RepresentationReservation;
import be.iccbxl.pid.reservationsspringboot.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepresentationReservationRepository extends CrudRepository<RepresentationReservation, Long> {
    List<RepresentationReservation> findByReservation(Reservation reservation);
    List<RepresentationReservation> findByRepresentation(Representation representation);
}