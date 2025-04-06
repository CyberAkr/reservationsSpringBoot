package be.iccbxl.pid.reservationsspringboot.repository;


import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.reservationsspringboot.model.Locality;


public interface LocalityRepository extends CrudRepository<Locality, Long> {
	Locality findByPostalCode(String postalCode);
	Locality findByLocality(String locality);
}
