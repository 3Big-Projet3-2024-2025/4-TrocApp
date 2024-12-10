package helha.trocappbackend.repositories;

import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {
    public List<User> findByFirstName(String firstName);
    public User findByFirstNameAndLastName(String firstName, String LastName);
}
