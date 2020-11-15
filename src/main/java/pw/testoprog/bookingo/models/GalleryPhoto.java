package pw.testoprog.bookingo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GalleryPhoto {
    @Id
    @GeneratedValue
    private int id;
}
