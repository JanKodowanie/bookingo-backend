package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import pw.testoprog.bookingo.models.GalleryPhoto;

import java.util.Optional;

@Repository
public interface GalleryPhotoRepository extends JpaRepository<GalleryPhoto, Integer> {
}
