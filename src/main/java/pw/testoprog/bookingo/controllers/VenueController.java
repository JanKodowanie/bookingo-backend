package pw.testoprog.bookingo.controllers;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;
import pw.testoprog.bookingo.serializers.MessageResponse;
import pw.testoprog.bookingo.serializers.VenueContext;

import javax.validation.Valid;

@RestController
public class VenueController {
    private final VenueRepository repository;
    private final UserRepository userRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    VenueController(VenueRepository repository, UserRepository userRepository, ServiceTypeRepository serviceTypeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    @GetMapping("/venues")
    List<Venue> all() {
        return repository.findAll();
    }

    @PostMapping("/venues")
    ResponseEntity newVenue(@RequestBody @Valid VenueContext venueContext) {
        Venue newVenue;
        try {
            newVenue = venueContext.getNewVenue(repository, userRepository, serviceTypeRepository);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newVenue);
    }

    @PutMapping("/venues/{id}")
    ResponseEntity replaceVenue(@RequestBody @Valid VenueContext venueContext, @PathVariable Integer id) {
        Optional<Venue> venue = repository.findById(id);
        if (venue.isPresent()) {
            Venue newVenue = venue.get();
            newVenue.setName(venueContext.getVenue().getName());
            newVenue.setCity(venueContext.getVenue().getCity());
            newVenue.setAddress(venueContext.getVenue().getAddress());
            try {
                newVenue.setUser(userRepository.findById(venueContext.getUser_id()).get());
                newVenue.setServiceTypes(new HashSet<>(serviceTypeRepository.findAllById(new HashSet<>(Arrays.asList(venueContext.getService_type_ids())))));
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(e.getMessage()));
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(repository.save(newVenue));
        } else {
            try {
                Venue newVenue = venueContext.getNewVenue(repository, userRepository, serviceTypeRepository);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(repository.save(newVenue));
            } catch (Exception e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new MessageResponse(e.getMessage()));
            }
        }

    }

    @DeleteMapping("/venues/{id}")
    ResponseEntity deleteVenues(@PathVariable Integer id) {
        try{
            repository.deleteById(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("");
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
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
