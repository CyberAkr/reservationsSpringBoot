package be.iccbxl.pid.reservationsspringboot.service;

import be.iccbxl.pid.reservationsspringboot.model.Price;
import be.iccbxl.pid.reservationsspringboot.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {
    @Autowired
    private PriceRepository repository;
    
    public List<Price> getAll() {
        List<Price> prices = new ArrayList<>();
        repository.findAll().forEach(prices::add);
        
        return prices;
    }
    
    public Optional<Price> get(Long id) {
        return repository.findById(id);
    }
    
    public void add(Price price) {
        repository.save(price);
    }
    
    public void update(Long id, Price price) {
        repository.save(price);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}