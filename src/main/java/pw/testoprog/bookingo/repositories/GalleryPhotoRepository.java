package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.testoprog.bookingo.models.GalleryPhoto;

public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Integer> {
}
