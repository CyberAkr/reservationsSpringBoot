package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "representation_reservation")
public class RepresentationReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "representation_id", nullable = false)
    private Representation representation;
    
    @ManyToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;
    
    @ManyToOne
    @JoinColumn(name = "price_id", nullable = false)
    private Price price;
    
    @Column
    private Integer quantity;

    protected RepresentationReservation() {}

    public RepresentationReservation(Representation representation, Reservation reservation, Price price, Integer quantity) {
        this.representation = representation;
        this.reservation = reservation;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "RepresentationReservation [id=" + id + 
               ", representation=" + (representation != null ? representation.getId() : "null") + 
               ", reservation=" + (reservation != null ? reservation.getId() : "null") + 
               ", price=" + (price != null ? price.getPrice() : "null") + 
               ", quantity=" + quantity + "]";
    }
}