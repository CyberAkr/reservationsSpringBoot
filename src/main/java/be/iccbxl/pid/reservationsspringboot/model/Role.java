package be.iccbxl.pid.reservationsspringboot.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name="roles")
public class Role {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String role;
	
	@OneToMany(mappedBy = "role")
    private List<UserRole> userRoles = new ArrayList<>();
	
	protected Role() {	}
	
	public Role(String role) {
		super();
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for(UserRole userRole : userRoles) {
            users.add(userRole.getUser());
        }
        return users;
    }
    
    // Méthode pour ajouter un utilisateur
    public Role addUser(User user) {
        UserRole userRole = new UserRole(user, this);
        userRoles.add(userRole);
        return this;
    }


	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}
	
}
