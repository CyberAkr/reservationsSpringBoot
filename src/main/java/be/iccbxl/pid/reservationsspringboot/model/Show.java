package be.iccbxl.pid.reservationsspringboot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.github.slugify.Slugify;

@Entity
@Table(name="shows")
public class Show {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String slug;

    private String title;
    private String description;

    @Column(name="poster_url")
    private String posterUrl;
    
    @ManyToOne
    @JoinColumn(name="location_id", nullable=true)
    private Location location;
    
    private boolean bookable;
    private double price;
    
    @Column(name="created_at")
    private LocalDateTime createdAt;
    
    @Column(name="updated_at")
    private LocalDateTime updatedAt;
    
	@OneToMany(mappedBy = "show")
    private List<Representation> representations = new ArrayList<>();

	@ManyToMany(mappedBy = "shows")
    private List<ArtistType> artistTypes = new ArrayList<>();
    
    @OneToMany(mappedBy = "show")
    private List<Review> reviews = new ArrayList<>();

    public Show() { }
    
    public Show(String title, String description, String posterUrl, Location location, boolean bookable,
            double price) {
        Slugify slg = new Slugify();
        
        this.slug = slg.slugify(title);
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.location = location;
        this.bookable = bookable;
        this.price = price;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }

    private void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        
        Slugify slg = new Slugify();
        this.setSlug(slg.slugify(title));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Location getLocation() {
        return location;
    }

	public void setLocation(Location location) {
        if (this.location != null) {
            this.location.getShows().remove(this);
        }
        this.location = location;
        if (location != null) {
            location.getShows().add(this);
        }
    }

    public boolean isBookable() {
        return bookable;
    }

    public void setBookable(boolean bookable) {
        this.bookable = bookable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Representation> getRepresentations() {
        return representations;
    }

    public Show addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setShow(this);
        }
        
        return this;
    }
    
    public Show removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            if(representation.getShow().equals(this)) {
                representation.setShow(null);
            }
        }
        
        return this;
    }

    public List<ArtistType> getArtistTypes() {
        return artistTypes;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
	public Show addReview(Review review) {
        if(!this.reviews.contains(review)) {
            this.reviews.add(review);
            review.setShow(this);
        }
        return this;
    }
    
	public Show removeReview(Review review) {
        if(this.reviews.contains(review)) {
            this.reviews.remove(review);
            if(review.getShow().equals(this)) {
                review.setShow(null);
            }
        }
        return this;
    }

    @Override
    public String toString() {
        return "Show [id=" + id + ", title=" + title + ", bookable=" + bookable 
            + ", price=" + price + ", representations=" + representations.size() 
            + ", artistTypes=" + artistTypes.size() + "]";
    }
}