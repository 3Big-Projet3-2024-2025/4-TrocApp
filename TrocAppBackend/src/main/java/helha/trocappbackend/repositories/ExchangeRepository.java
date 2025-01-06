package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Exchange entities.
 *
 * <p>This interface extends JpaRepository, providing standard CRUD operations
 * and additional query methods for the Exchange entity.</p>
 * @author Hayriye Dogan
 * @see helha.trocappbackend.repositories
 */
public interface ExchangeRepository extends JpaRepository<Exchange, Integer> {
}
