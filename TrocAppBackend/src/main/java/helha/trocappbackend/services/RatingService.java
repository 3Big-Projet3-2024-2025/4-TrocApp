package helha.trocappbackend.services;

import helha.trocappbackend.models.Rating;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.RatingRepository;
import helha.trocappbackend.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing ratings.
 */
@Service
public class RatingService {

    /**
     * Repository for rating-related operations.
     */
    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Repository for user-related operations.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a new rating.
     *
     * @param rating the rating to add
     * @return the added rating
     */
    public Rating addRating(Rating rating) {
        // Load the poster and receiver from their IDs
        User poster = userRepository.findById(rating.getPoster().getId())
                .orElseThrow(() -> new RuntimeException("Poster not found"));
        User receiver = userRepository.findById(rating.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        rating.setPoster(poster);
        rating.setReceiver(receiver);

        return ratingRepository.save(rating);
    }

    /**
     * Retrieves the ratings received by a user.
     *
     * @param userId the ID of the user who received the ratings
     * @return a list of ratings received by the user
     */
    public List<Rating> getReceivedRatings(int userId) {
        return ratingRepository.findByReceiverId(userId);
    }

    /**
     * Retrieves the ratings posted by a user.
     *
     * @param userId the ID of the user who posted the ratings
     * @return a list of ratings posted by the user
     */
    public List<Rating> getPostedRatings(int userId) {
        return ratingRepository.findByPosterId(userId);
    }

    /**
     * Deletes a rating by its ID.
     *
     * @param ratingId the ID of the rating to delete
     */
    @Transactional
    public void deleteRating(int ratingId) {
        if (!ratingRepository.existsById(ratingId)) {
            throw new RuntimeException("Rating not found");
        }
        ratingRepository.deleteById(ratingId);
    }
}