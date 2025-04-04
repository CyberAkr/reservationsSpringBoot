package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;




@Entity
@Table(name="localities")
public class Locality {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String postalCode;
	private String locality;
	
	protected Locality() {	}

	public Locality(String postalCode, String locality) {
		this.postalCode = postalCode;
		this.locality = locality;
	}

	public Long getId() {
		return id;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getLocality() {
		return locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	@Override
	public String toString() {
		return "Locality [id=" + id + ", postalCode=" + postalCode + ", locality=" + locality + "]";
	}
	
}

