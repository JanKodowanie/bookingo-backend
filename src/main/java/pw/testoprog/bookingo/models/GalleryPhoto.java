package pw.testoprog.bookingo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class GalleryPhoto {
    @Id
    @GeneratedValue
    private int id;

    private String url;

    @ManyToOne()
    @JsonIgnore
    private Venue venue;

    protected GalleryPhoto() {}

    public GalleryPhoto(String url, Venue venue) {
        this.url = url;
        this.venue = venue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
