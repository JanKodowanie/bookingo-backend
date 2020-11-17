package pw.testoprog.bookingo.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String city;
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToMany(targetEntity = ServiceType.class, fetch = FetchType.EAGER)
    private Set<ServiceType> serviceTypes;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<GalleryPhoto> galleryPhotos;

    public Venue() {

    }

    public Venue(String name, String city, String address, User user, Set<ServiceType> serviceTypes) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.user = user;
        this.serviceTypes = serviceTypes;
    }

    @Override
    public String toString() {
        return String.format("Salon %s \"%s\", %s, %s. Oferowane usługi: %s", this.user, this.name, this.city, this.address, this.serviceTypes.toString());
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<ServiceType> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(Set<ServiceType> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue venue = (Venue) o;
        return name.equals(venue.name) &&
                city.equals(venue.city) &&
                address.equals(venue.address) &&
                user.getId().equals(venue.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, address, user.getId());
    }

    @Override
    public Venue clone() throws CloneNotSupportedException {
        return new Venue(this.name, this.city, this.address, this.user, this.serviceTypes);
    }

    public Set<GalleryPhoto> getGalleryPhotos() {
        return galleryPhotos;
    }

    public void setGalleryPhotos(Set<GalleryPhoto> galleryPhotos) {
        this.galleryPhotos = galleryPhotos;
    }
}
