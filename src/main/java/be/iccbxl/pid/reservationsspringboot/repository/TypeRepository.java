package be.iccbxl.pid.reservationsspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.reservationsspringboot.model.Type;


public interface TypeRepository extends CrudRepository<Type, Long> {
	Type findByType(String type);
}
