package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing Role entities.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, PagingAndSortingRepository<Role, Integer> {

    /**
     * Finds a role by its name.
     *
     * @param name the name of the role
     * @return an Optional containing the role if found, or empty if not found
     */
    Optional<Role> findByName(String name);

    /**
     * Finds a role by its unique identifier.
     *
     * @param id the unique identifier of the role
     * @return an Optional containing the role if found, or empty if not found
     */
    Optional<Role> findById(int id);
}