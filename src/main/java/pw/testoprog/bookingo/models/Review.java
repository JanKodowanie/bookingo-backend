package pw.testoprog.bookingo.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    private String content;
    private Date creationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Venue venue;

    @ManyToOne(fetch = FetchType.EAGER)
    private ServiceType serviceType;

    public Review() { }

    public Review(User user, String content, Venue venue, ServiceType serviceType, Date creationDate) {
        this.user = user;
        this.creationDate = creationDate;
        this.content = content;
        this.venue = venue;
        this.serviceType = serviceType;
    }

    public Review(User user, String content, Venue venue, ServiceType serviceType) {
        this.user = user;
        this.content = content;
        this.venue = venue;
        this.serviceType = serviceType;
        this.creationDate = java.sql.Timestamp.valueOf(LocalDateTime.now());
    }

    public User getUser() {
        return user;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {this.id = id;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return user.getId().equals(review.user.getId()) &&
                content.equals(review.content) &&
                venue.getId().equals(review.venue.getId()) &&
                serviceType.getId().equals(review.serviceType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, content, creationDate, venue, serviceType);
    }


    @Override
    public Review clone() {
        return new Review(this.user, this.content, this.venue, this.serviceType, this.creationDate);
    }

}
