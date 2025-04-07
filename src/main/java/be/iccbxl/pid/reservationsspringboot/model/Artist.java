package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;	
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="artists")
public class Artist {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String firstname;
	private String lastname;
	
	@JsonIgnore
	
	
	@OneToMany(mappedBy = "artist")
private List<ArtistType> artistTypes = new ArrayList<>();



	public Artist() {}

	public Artist(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
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
	
	

	public List<Type> getTypes() {
        List<Type> types = new ArrayList<>();
        for(ArtistType artistType : this.artistTypes) {
            types.add(artistType.getType());
        }
        return types;
    }
    
    // Méthode d'ajout à adapter
    public Artist addType(Type type) {
        ArtistType artistType = new ArtistType();
        artistType.setArtist(this);
        artistType.setType(type);
        if(!this.artistTypes.contains(artistType)) {
            this.artistTypes.add(artistType);
        }
        return this;
    }
    
    public Artist removeType(Type type) {
        ArtistType toRemove = null;
        for(ArtistType at : artistTypes) {
            if(at.getType().equals(type)) {
                toRemove = at;
                break;
            }
        }
        if(toRemove != null) {
            this.artistTypes.remove(toRemove);
        }
        return this;
    }

	public void setId(Long id) {
		this.id = id;
	}
}