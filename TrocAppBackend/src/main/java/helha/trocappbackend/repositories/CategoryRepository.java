package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link Category} entities.
 * This interface extends {@link JpaRepository} to provide the standard persistence operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
