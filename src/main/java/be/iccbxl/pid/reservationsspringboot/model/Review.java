package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
    
    @Column(columnDefinition = "text")
    private String review;
    
    @Column
    private Byte stars;
    
    @Column
    private Boolean validated;
    
    @Column
    private Date created_at;
    
    @Column
    private Date updated_at;

    public  Review() {}

    public Review(User user, Show show, String review, Byte stars, Boolean validated, Date created_at, Date updated_at) {
        this.user = user;
        this.show = show;
        this.review = review;
        this.stars = stars;
        this.validated = validated;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Byte getStars() {
        return stars;
    }

    public void setStars(Byte stars) {
        this.stars = stars;
    }

    public Boolean getValidated() {
        return validated;
    }

    public void setValidated(Boolean validated) {
        this.validated = validated;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user.getLogin() +
                ", show=" + show.getTitle() +
                ", stars=" + stars +
                ", validated=" + validated +
                '}';
    }
}