package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pw.testoprog.bookingo.models.Venue;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
    @Query("select v from Venue v join v.serviceTypes st where st.id = ?1")
    List<Venue> findByServiceType(Integer serviceTypeId);
}
