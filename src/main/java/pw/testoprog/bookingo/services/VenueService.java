package pw.testoprog.bookingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.testoprog.bookingo.exceptions.InvalidVenueException;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.exceptions.VenueNotFoundException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;

import java.util.Optional;
import java.util.List;


@Service
public class VenueService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VenueRepository venueRepository;

    public Venue addNewVenue(Venue newVenue) throws UserNotFoundException, InvalidVenueException {
        if( newVenue == null )
            throw new InvalidVenueException("Invalid venue");

        Optional<User> registeredUser = userRepository.findById(newVenue.getUser().getId());

        if(registeredUser.isPresent()) {
            venueRepository.save(newVenue);
            return newVenue;
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    public Venue getVenueById(Integer id) throws VenueNotFoundException {
        Optional<Venue> optionalVenue = venueRepository.findById(id);

        if(!optionalVenue.isPresent()) {
            throw new VenueNotFoundException("Venue not found");
        }
        return optionalVenue.get();
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    public Venue updateVenue(Venue newVenue, Integer id) throws VenueNotFoundException, InvalidVenueException {
        if( newVenue == null )
            throw new InvalidVenueException("Invalid venue");

        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if(!optionalVenue.isPresent()) {
            throw new VenueNotFoundException("Venue not found");
        }
        Venue updatedVenue = optionalVenue.get();
        updatedVenue.setAddress(newVenue.getAddress());
        updatedVenue.setCity(newVenue.getCity());
        updatedVenue.setName(newVenue.getName());
        updatedVenue.setServiceTypes(newVenue.getServiceTypes());
        updatedVenue.setUser(newVenue.getUser());
        return updatedVenue;
    }

    public void deleteVenue(Integer id) throws VenueNotFoundException {
        Optional<Venue> optionalVenue = venueRepository.findById(id);
        if( !optionalVenue.isPresent()) {
            throw new VenueNotFoundException("Venue not found");
        }
        venueRepository.deleteById(id);
    }

    public List<Venue> getVenuesByServiceTypeId(Integer id) {
        return venueRepository.findByServiceType(id);
    }

}
