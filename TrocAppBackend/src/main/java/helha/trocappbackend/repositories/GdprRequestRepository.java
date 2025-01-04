package helha.trocappbackend.repositories;

import helha.trocappbackend.models.GdprRequest;
import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GdprRequestRepository extends JpaRepository<GdprRequest, Integer>  {
    List<GdprRequest> findByUser(User user);

}
