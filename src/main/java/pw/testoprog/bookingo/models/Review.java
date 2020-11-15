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

    @ManyToOne()
    private User user;

    private String content;
    private Date creationDate;

    @ManyToOne()
    private Venue venue;

    @ManyToOne()
    private ServiceType serviceType;

    private Review(User user, String content, Venue venue, ServiceType serviceType, Date creationDate) {
        this.user = user;
        this.creationDate = creationDate;
        this.content = content;
        this.venue = venue;
        this.serviceType = serviceType;
        this.creationDate = java.sql.Timestamp.valueOf(LocalDateTime.now());
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
        return user.equals(review.user) &&
                content.equals(review.content) &&
                creationDate.equals(review.creationDate) &&
                venue.equals(review.venue) &&
                serviceType.equals(review.serviceType);
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
