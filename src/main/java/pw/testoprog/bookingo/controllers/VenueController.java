package pw.testoprog.bookingo.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.responses.MessageResponse;
import pw.testoprog.bookingo.services.VenueService;

import javax.validation.Valid;

@RestController
public class VenueController {

    @Autowired
    VenueService venueService;

    @GetMapping("/venues")
    List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    @PostMapping("/venues")
    ResponseEntity newVenue(@RequestBody @Valid Venue newVenue) {
        try {
            newVenue = venueService.addNewVenue(newVenue);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(newVenue);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/venues/{id}")
    ResponseEntity getVenueById(@PathVariable Integer id ) {
        try{
            Venue requestedVenue = venueService.getVenueById(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(requestedVenue);
        }
        catch( Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/venues/{id}")
    ResponseEntity updateVenue(@RequestBody @Valid Venue newVenue, @PathVariable Integer id) {
        try{
            Venue updatedVenue = venueService.updateVenue(newVenue, id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updatedVenue);
        } catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/venues/{id}")
    ResponseEntity deleteVenue(@PathVariable Integer id) {
        try{
            venueService.deleteVenue(id);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
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
        return venueService.getVenuesByServiceTypeId(serviceTypeId);
    }
}
