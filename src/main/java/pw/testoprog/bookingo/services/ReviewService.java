package pw.testoprog.bookingo.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.testoprog.bookingo.exceptions.*;
import pw.testoprog.bookingo.models.Review;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ReviewRepository;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Autowired
    UserRepository userRepository;

    public Review addReview(Review newReview, Integer venueId, Integer serviceId) throws InvalidReviewException, VenueNotFoundException, ServiceTypeNotFoundException {
        if( newReview == null)
            throw new InvalidReviewException("Invalid review");

        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceId);

        if(!optionalVenue.isPresent()) {
            throw new VenueNotFoundException("Incorrect Venue Id");
        }
        if(!optionalServiceType.isPresent()) {
            throw new ServiceTypeNotFoundException("Incorrect Service Type Id");
        }
        reviewRepository.save(newReview);
        return newReview;
    }

    public Review getReviewById(Integer id) throws ReviewNotFoundException {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (!optionalReview.isPresent()){
            throw new ReviewNotFoundException("No review with given id");
        }
        return optionalReview.get();
    }

    public void deleteReview(Integer id, UUID userId) throws UserNotFoundException, UnauthorizedUserException, ReviewNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if(!optionalUser.isPresent()){
            throw new UserNotFoundException("No user with given id");
        }
        if(!optionalReview.isPresent()) {
            throw new ReviewNotFoundException("No review with given id");
        }
        if(optionalUser.get().getId() != optionalReview.get().getUser().getId()) {
            throw new UnauthorizedUserException("You can only delete your reviews");
        }
        reviewRepository.delete(optionalReview.get());
    }


}
