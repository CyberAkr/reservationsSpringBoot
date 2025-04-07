package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30)
    private String type;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    private Date start_date;

    @Column
    private Date end_date;

     @OneToMany(mappedBy = "price")
    private List<RepresentationReservation> representationReservations = new ArrayList<>();

    // Changement de protected à public
    public Price() {}

    public Price(String type, BigDecimal price, Date start_date, Date end_date) {
        this.type = type;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}