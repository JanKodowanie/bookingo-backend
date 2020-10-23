package pw.testoprog.bookingo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.VenueRepository;

@RestController
public class VenueController {
    private final VenueRepository repository;

    VenueController(VenueRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/venues")
    List<Venue> all() {
        return repository.findAll();
    }

    @PostMapping("/venues")
    Venue newVenue(@RequestBody Venue newVenue) {
        return repository.save(newVenue);
    }

    @PutMapping("/venues/{id}")
    Venue replaceVenue(@RequestBody Venue newVenue, @PathVariable Integer id) {
        return repository.findById(id)
                .map(venue -> {
                    venue.setName(newVenue.getName());
                    venue.setCity(newVenue.getCity());
                    venue.setAddress(newVenue.getAddress());
                    venue.setUser(newVenue.getUser());
                    venue.setServiceTypes(newVenue.getServiceTypes());
                    return repository.save(venue);
                })
                .orElseGet(() -> {
                    newVenue.setId(id);
                    return repository.save(newVenue);
                });
    }

    @DeleteMapping("/venues/{id}")
    void deleteVenues(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    /**
     * Zwraca salony oferujace dana usluge.
     * @param serviceTypeId ID danego typu uslugi
     * @return lista salonow
     */
    @GetMapping("/venues/service-type/{serviceTypeId}")
    List<Venue> venuesByServiceType(@PathVariable Integer serviceTypeId) {
        return repository.findByServiceType(serviceTypeId);
    }
}
