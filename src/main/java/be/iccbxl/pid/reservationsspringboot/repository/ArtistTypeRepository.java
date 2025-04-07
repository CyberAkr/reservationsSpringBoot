package be.iccbxl.pid.reservationsspringboot.repository;

import be.iccbxl.pid.reservationsspringboot.model.Artist;
import be.iccbxl.pid.reservationsspringboot.model.ArtistType;
import be.iccbxl.pid.reservationsspringboot.model.Type;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistTypeRepository extends CrudRepository<ArtistType, Long> {
    List<ArtistType> findByArtist(Artist artist);
    List<ArtistType> findByType(Type type);
}