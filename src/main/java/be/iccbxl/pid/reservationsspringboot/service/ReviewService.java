package be.iccbxl.pid.reservationsspringboot.service;

import be.iccbxl.pid.reservationsspringboot.model.Review;
import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.model.User;
import be.iccbxl.pid.reservationsspringboot.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository repository;
    
    public List<Review> getAll() {
        List<Review> reviews = new ArrayList<>();
        repository.findAll().forEach(reviews::add);
        
        return reviews;
    }
    
    public Optional<Review> get(Long id) {
        return repository.findById(id);
    }
    
    public List<Review> getByShow(Show show) {
        return repository.findByShow(show);
    }
    
    public List<Review> getByUser(User user) {
        return repository.findByUser(user);
    }
    
    public List<Review> getByValidated(Boolean validated) {
        return repository.findByValidated(validated);
    }
    
    public void add(Review review) {
        repository.save(review);
    }
    
    public void update(Long id, Review review) {
        repository.save(review);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}