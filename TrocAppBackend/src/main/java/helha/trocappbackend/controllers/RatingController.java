package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Rating;
import helha.trocappbackend.services.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing ratings.
 */
@RestController
@RequestMapping("/ratings")
public class RatingController {

    /**
     * Service for rating-related operations.
     */
    @Autowired
    private RatingService ratingService;

    /**
     * Add a new rating.
     *
     * @param rating the rating to add
     * @return the added rating
     */
    @PostMapping("/add")
    public Rating addRating(@Valid @RequestBody Rating rating) {
        return ratingService.addRating(rating);
    }

    /**
     * Get the ratings received by a user.
     *
     * @param userId the user ID
     * @return a list of received ratings
     */
    @GetMapping("/received/{userId}")
    public List<Rating> getReceivedRatings(@PathVariable int userId) {
        return ratingService.getReceivedRatings(userId);
    }

    /**
     * Get the ratings posted by a user.
     *
     * @param userId the user ID
     * @return a list of posted ratings
     */
    @GetMapping("/posted/{userId}")
    public List<Rating> getPostedRatings(@PathVariable int userId) {
        return ratingService.getPostedRatings(userId);
    }

    /**
     * Delete a rating by ID.
     *
     * @param ratingId the rating ID
     * @return a response entity with status 200 (OK)
     */
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRating(@PathVariable int ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get the average rating of a user.
     *
     * @param userId the user ID
     * @return the average rating
     */
    @GetMapping("/average/{userId}")
    public Double getAverageRating(@PathVariable int userId) {
        List<Rating> ratings = ratingService.getReceivedRatings(userId);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream()
                .mapToDouble(Rating::getNumberStars)
                .average()
                .orElse(0.0);
    }
}