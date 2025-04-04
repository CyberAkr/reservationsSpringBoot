package be.iccbxl.pid.reservationsspringboot.repository;

import be.iccbxl.pid.reservationsspringboot.model.Show;
import be.iccbxl.pid.reservationsspringboot.model.Location;


import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ShowRepository extends CrudRepository<Show, Long> {
	Show findBySlug(String slug);
	Show findByTitle(String title);
	List<Show> findByLocation(Location location);

}
