package be.iccbxl.pid.reservationsspringboot.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Setter;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Getter;

	
@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) // Changez AUTO à IDENTITY
	private Long id;
	private String login;
	private String password;
	private String firstname;
	private String lastname;
private String email;
	private String langue;
	private LocalDateTime created_at;
	
    @Setter
	@Getter
	
	@OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();
    
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();
    
    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();
    
    
    // Méthode auxiliaire pour obtenir les rôles
    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        for(UserRole userRole : userRoles) {
            roles.add(userRole.getRole());
        }
        return roles;
    }
	
    
    // Méthode pour ajouter un rôle
    public User addRole(Role role) {
        UserRole userRole = new UserRole(this, role);
        userRoles.add(userRole);
        return this;
    }
	public User() {}

	
	
	
	public User(String login, String firstname, String lastname) {
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.created_at = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	 
        public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	@Override
	public String toString() {
		return login + "(" + firstname + " " + lastname + ")";
	}

	public List<User> getRepresentations() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getRepresentations'");
	}

    public void addRepresentation(Representation representation) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addRepresentation'");
    }
public LocalDateTime getCreatedAt() {
    return created_at;
}

public void setCreatedAt(LocalDateTime created_at) {
    this.created_at = created_at;
}
}
