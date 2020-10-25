package pw.testoprog.bookingo.serializers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pw.testoprog.bookingo.exceptions.ServiceTypeNotFoundException;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class VenueContext {
    private Venue venue;
    private UUID user_id;
    private Integer[] service_type_ids;

    public VenueContext(Venue venue, UUID user_id, Integer[] service_type_ids) {
        this.venue = venue;
        this.user_id = user_id;
        this.service_type_ids = service_type_ids;
    }

    public Venue getNewVenue(VenueRepository repository, UserRepository userRepository, ServiceTypeRepository serviceTypeRepository) throws UserNotFoundException, ServiceTypeNotFoundException {
        Venue newVenue = this.getVenue();
        Optional<User> user = userRepository.findById(this.getUser_id());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        newVenue.setUser(user.get());

        Set<ServiceType> serviceTypes = new HashSet<>();
        for (int serviceTypeId : this.getService_type_ids()) {
            Optional<ServiceType> serviceType = serviceTypeRepository.findById(serviceTypeId);
            if (!serviceType.isPresent()) {
                throw new ServiceTypeNotFoundException("Service type not found");
            } else {
                serviceTypes.add(serviceType.get());
            }
        }
        newVenue.setServiceTypes(serviceTypes);

        return repository.save(newVenue);
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public Integer[] getService_type_ids() {
        return service_type_ids;
    }

    public void setService_type_ids(Integer[] service_type_ids) {
        this.service_type_ids = service_type_ids;
    }
}
