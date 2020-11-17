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

import java.util.List;
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

    public Review addReview(Review newReview, Integer venueId) throws UserNotFoundException, InvalidReviewException, VenueNotFoundException, ServiceTypeNotFoundException {
        if( newReview == null)
            throw new InvalidReviewException("Invalid review");


        Optional<User> optionalUser = userRepository.findById(newReview.getUser().getId());
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(newReview.getServiceType().getId());

        if(!optionalUser.isPresent()) {
            throw new UserNotFoundException("Incorrect user Id");
        }
        if(!optionalVenue.isPresent()) {
            throw new VenueNotFoundException("Incorrect Venue Id");
        }
        if(!optionalServiceType.isPresent()) {
            throw new ServiceTypeNotFoundException("Incorrect Service Type Id");
        }

        Review fullReview = new Review(optionalUser.get(), newReview.getContent(), optionalVenue.get(),optionalServiceType.get());
        reviewRepository.save(fullReview);
        return fullReview;
    }

    public Review getReviewById(Integer id) throws ReviewNotFoundException {
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if (!optionalReview.isPresent()){
            throw new ReviewNotFoundException("No review with given id");
        }
        return optionalReview.get();
    }

    public void deleteReview(Integer id, User user) throws UserNotFoundException, UnauthorizedUserException, ReviewNotFoundException {
        Optional<User> optionalUser = userRepository.findById(user.getId());
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

    public List<Review> getAllVenueReviews(Integer venueId) throws VenueNotFoundException {
        Optional<Venue> optionalVenue = venueRepository.findById(venueId);

        if(!optionalVenue.isPresent()) {
            throw new VenueNotFoundException("No venue with given id");
        }
        List<Review> reviewList = reviewRepository.findByVenueId(venueId);
        return reviewList;

    }

    public Review updateReview(Integer reviewId, Review newReview) throws UserNotFoundException, UnauthorizedUserException, ReviewNotFoundException {
        Optional<User> optionalUser = userRepository.findById(newReview.getUser().getId());
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if( !optionalUser.isPresent()) {
            throw new UserNotFoundException("No user with given id");
        }
        if( !optionalReview.isPresent()) {
            throw new ReviewNotFoundException("Not review with given id");
        }
        if (optionalReview.isPresent() && optionalUser.get().getId() != optionalReview.get().getUser().getId() ) {
            throw new UnauthorizedUserException("You can only edit your own reviews");
        }

        Review updatedReview = optionalReview.get();
        updatedReview.setContent(newReview.getContent());
        return updatedReview;
    }

}
