package helha.trocappbackend.repositories;

import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

    /**
     * Finds users by their first name.
     *
     * @param firstName the first name of the user
     * @return a list of users with the given first name
     */
    List<User> findByFirstName(String firstName);

    /**
     * Finds users by their last name.
     *
     * @param lastName the last name of the user
     * @return a list of users with the given last name
     */
    List<User> findByLastName(String lastName);

    /**
     * Finds users by their email address.
     *
     * @param email the email address of the user
     * @return a list of users with the given email address
     */
    List<User> findByEmail(String email);

    /**
     * Finds a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findById(int id);

    /**
     * Gets a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return a list of users with the given identifier
     */
    List<User> getUserById(int id);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user
     * @return the user with the given username
     */
    User findByUsername(String username);
}