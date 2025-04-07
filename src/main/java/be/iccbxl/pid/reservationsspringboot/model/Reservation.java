package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column
    private Date booking_date;
    
    @Column(length = 60)
    private String status;
    
    @OneToMany(targetEntity=RepresentationReservation.class, mappedBy="reservation", cascade=CascadeType.ALL)
    private List<RepresentationReservation> representationReservations = new ArrayList<>();

    protected Reservation() {}

    public Reservation(User user, Date booking_date, String status) {
        this.user = user;
        this.booking_date = booking_date;
        this.status = status;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(Date booking_date) {
        this.booking_date = booking_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public List<RepresentationReservation> getRepresentationReservations() {
        return representationReservations;
    }
    
    public Reservation addRepresentationReservation(RepresentationReservation representationReservation) {
        if(!this.representationReservations.contains(representationReservation)) {
            this.representationReservations.add(representationReservation);
            representationReservation.setReservation(this);
        }
        
        return this;
    }
    
    public Reservation removeRepresentationReservation(RepresentationReservation representationReservation) {
        if(this.representationReservations.contains(representationReservation)) {
            this.representationReservations.remove(representationReservation);
            if(representationReservation.getReservation().equals(this)) {
                representationReservation.setReservation(null);
            }
        }
        
        return this;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", user=" + user.getLogin() +
                ", booking_date=" + booking_date +
                ", status='" + status + '\'' +
                '}';
    }
}