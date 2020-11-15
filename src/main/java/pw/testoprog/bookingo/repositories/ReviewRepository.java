package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pw.testoprog.bookingo.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
