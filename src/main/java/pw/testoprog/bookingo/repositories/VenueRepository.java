package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pw.testoprog.bookingo.models.Venue;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    @Query("select v from Venue v join v.serviceTypes st where st.id = ?1")
    List<Venue> findByServiceType(Long serviceTypeId);
}
