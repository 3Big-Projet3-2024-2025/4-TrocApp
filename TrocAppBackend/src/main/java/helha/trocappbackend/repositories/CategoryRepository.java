package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Category;
//import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}