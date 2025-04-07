package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="types")
public class Type {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String type;
	
	 @OneToMany(mappedBy = "type")
    private List<ArtistType> artistTypes = new ArrayList<>();

	public Type(Long id, String type) {
		super();
		this.id = id;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Artist> getArtists() {
        List<Artist> artists = new ArrayList<>();
        for(ArtistType artistType : this.artistTypes) {
            artists.add(artistType.getArtist());
        }
        return artists;
    }
    
    // Méthode d'ajout à adapter
    public Type addArtist(Artist artist) {
        ArtistType artistType = new ArtistType();
        artistType.setArtist(artist);
        artistType.setType(this);
        if(!hasArtistType(artistType)) {
            this.artistTypes.add(artistType);
        }
        return this;
    }
    
    public Type removeArtist(Artist artist) {
        ArtistType toRemove = null;
        for(ArtistType at : artistTypes) {
            if(at.getArtist().equals(artist)) {
                toRemove = at;
                break;
            }
        }
        if(toRemove != null) {
            this.artistTypes.remove(toRemove);
        }
        return this;
    }
    
    private boolean hasArtistType(ArtistType artistType) {
        for(ArtistType at : artistTypes) {
            if(at.getArtist().equals(artistType.getArtist()) && 
               at.getType().equals(artistType.getType())) {
                return true;
            }
        }
        return false;
    }
}