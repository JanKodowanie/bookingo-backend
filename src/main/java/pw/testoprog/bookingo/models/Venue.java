package pw.testoprog.bookingo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String city;
    private String address;

    @ManyToOne()
    private User user;

    @ManyToMany(targetEntity = ServiceType.class)
    private Set<ServiceType> serviceTypes;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
