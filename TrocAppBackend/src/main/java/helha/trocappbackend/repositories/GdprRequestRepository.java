package helha.trocappbackend.repositories;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link GdprRequest} entities.
 * This interface extends {@link JpaRepository} to provide the standard persistence operations.
 */
public interface GdprRequestRepository extends JpaRepository<GdprRequest, Integer> {

    /**
     * Finds all GDPR requests associated with a specific user.
     *
     * @param user The user whose GDPR requests are to be retrieved.
     * @return A list of {@link GdprRequest} entities associated with the given user.
     */
    List<GdprRequest> findByUser(User user);
}
