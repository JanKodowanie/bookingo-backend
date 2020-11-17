package pw.testoprog.bookingo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Integer> {
    @Query("select v from Venue v join v.serviceTypes st where st.id = ?1")
    List<Venue> findByServiceType(Integer serviceTypeId);

    Optional<Venue> findById(Integer id);
}
