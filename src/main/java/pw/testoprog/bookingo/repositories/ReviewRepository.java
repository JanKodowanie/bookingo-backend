package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pw.testoprog.bookingo.models.Review;
import pw.testoprog.bookingo.models.Venue;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("select r from Review r join r.venue v where v.id = ?1")
    List<Review> findByVenueId(Integer venueTypeId);
}
