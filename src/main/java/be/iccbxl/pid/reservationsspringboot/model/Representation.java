package be.iccbxl.pid.reservationsspringboot.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import com.github.slugify.Slugify;

@Entity
@Table(name="representations")
public class Representation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name="show_id", nullable=false)
	private Show show;

	/**
	 * Date de création de la représentation
	 */
	private LocalDateTime when;
	
	/**
	 * Lieu de prestation de la représentation
	 */
	@ManyToOne
	@JoinColumn(name="location_id", nullable=true)
	private Location location;

	public Representation() { }
	
	public Representation(Show show, LocalDateTime when, Location location) {
		this.show = show;
		this.when = when;
		this.location = location;
	}

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public LocalDateTime getWhen() {
		return when;
	}

	public void setWhen(LocalDateTime when) {
		this.when = when;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Representation [id=" + id + ", show=" + show + ", when=" + when 
			+ ", location=" + location + "]";
	}
	
}
