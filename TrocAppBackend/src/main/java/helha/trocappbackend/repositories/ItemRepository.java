package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for managing Item entities.
 *
 * <p>This interface extends JpaRepository, providing standard CRUD operations
 * and additional query methods for the Item entity.</p>
 *  @author Hayriye Dogan
 *  @see helha.trocappbackend.repositories
 */
public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwner(User owner);
}
