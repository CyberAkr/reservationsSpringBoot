package be.iccbxl.pid.reservationsspringboot.service;

import be.iccbxl.pid.reservationsspringboot.model.Reservation;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository repository;
    
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        repository.findAll().forEach(reservations::add);
        
        return reservations;
    }
    
    public Optional<Reservation> get(Long id) {
        return repository.findById(id);
    }
    
    public List<Reservation> getByUser(User user) {
        return repository.findByUser(user);
    }
    
    public List<Reservation> getByStatus(String status) {
        return repository.findByStatus(status);
    }
    
    public void add(Reservation reservation) {
        repository.save(reservation);
    }
    
    public void update(Long id, Reservation reservation) {
        repository.save(reservation);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}