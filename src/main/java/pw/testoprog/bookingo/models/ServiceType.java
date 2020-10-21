package pw.testoprog.bookingo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class ServiceType {
    private @Id @GeneratedValue Long id;
    private String name;

    @ManyToMany(targetEntity = Venue.class)
    private Set<Venue> venues;

    protected ServiceType() {

    }

    public ServiceType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Venue> getVenues() {
        return venues;
    }

    public void setVenues(Set<Venue> venues) {
        this.venues = venues;
    }

    public void addVenue(Venue venue) {
        this.venues.add(venue);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
