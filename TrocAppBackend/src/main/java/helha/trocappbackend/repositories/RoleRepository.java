package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Role;
import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> , PagingAndSortingRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Optional<Role> findById(int id);
}