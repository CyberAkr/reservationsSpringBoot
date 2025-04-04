package be.iccbxl.pid.reservationsspringboot.repository;

import org.springframework.data.repository.CrudRepository;
import be.iccbxl.pid.reservationsspringboot.model.Role;


public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByRole(String role);
}
