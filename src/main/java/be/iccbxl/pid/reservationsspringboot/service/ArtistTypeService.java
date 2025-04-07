package be.iccbxl.pid.reservationsspringboot.service;

import be.iccbxl.pid.reservationsspringboot.model.Artist;
import be.iccbxl.pid.reservationsspringboot.model.ArtistType;
import be.iccbxl.pid.reservationsspringboot.model.Type;
import be.iccbxl.pid.reservationsspringboot.repository.ArtistTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistTypeService {
    @Autowired
    private ArtistTypeRepository repository;
    
    public List<ArtistType> getAll() {
        List<ArtistType> artistTypes = new ArrayList<>();
        repository.findAll().forEach(artistTypes::add);
        
        return artistTypes;
    }
    
    public Optional<ArtistType> get(Long id) {
        return repository.findById(id);
    }
    
    public List<ArtistType> getByArtist(Artist artist) {
        return repository.findByArtist(artist);
    }
    
    public List<ArtistType> getByType(Type type) {
        return repository.findByType(type);
    }
    
    public void add(ArtistType artistType) {
        repository.save(artistType);
    }
    
    public void update(Long id, ArtistType artistType) {
        repository.save(artistType);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}