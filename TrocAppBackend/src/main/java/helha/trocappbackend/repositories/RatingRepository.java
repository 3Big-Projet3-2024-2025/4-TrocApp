package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing Rating entities.
 */
public interface RatingRepository extends JpaRepository<Rating, Long> {

    /**
     * Retrieves the ratings received by a user.
     *
     * @param receiverId the ID of the user who received the ratings
     * @return a list of ratings received by the user
     */
    List<Rating> findByReceiverId(int receiverId);

    /**
     * Retrieves the ratings posted by a user.
     *
     * @param posterId the ID of the user who posted the ratings
     * @return a list of ratings posted by the user
     */
    List<Rating> findByPosterId(int posterId);

    /**
     * Deletes a rating by its ID.
     *
     * @param ratingId the ID of the rating to delete
     */
    void deleteById(int ratingId);

    /**
     * Checks if a rating exists by its ID.
     *
     * @param ratingId the ID of the rating to check
     * @return true if the rating exists, false otherwise
     */
    boolean existsById(int ratingId);
}