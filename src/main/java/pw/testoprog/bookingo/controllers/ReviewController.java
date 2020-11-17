package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pw.testoprog.bookingo.exceptions.ReviewNotFoundException;
import pw.testoprog.bookingo.exceptions.UnauthorizedUserException;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.Review;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.services.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/venues/{id}/reviews")
    ResponseEntity postReview(@PathVariable Integer id, @RequestBody @Valid Review newReview) {
        try {
            Review postReview = reviewService.addReview(newReview, id);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(postReview);
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/review/{id}")
    ResponseEntity getReviewById(@PathVariable Integer id) {
        try {
            Review review = reviewService.getReviewById(id);
            System.out.println(review.getId());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(review);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/venues/{venueId}/reviews")
    ResponseEntity getAllVenueReviews(@PathVariable Integer venueId) {
        try {
            List<Review> reviewList = reviewService.getAllVenueReviews(venueId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(reviewList);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/review/{id}")
    ResponseEntity deleteVenueById(@PathVariable Integer id, @RequestBody User user) {
        try {
            reviewService.deleteReview(id, user);
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body("");
        } catch (UnauthorizedUserException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (ReviewNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/review/{id}")
    ResponseEntity putReviewAtId(@PathVariable Integer id, @RequestBody @Valid Review review) {
        try {
            Review updatedReview = reviewService.updateReview(id, review);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(updatedReview);
        } catch (UnauthorizedUserException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        } catch (ReviewNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


}
