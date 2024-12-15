package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // Récupérer les évaluations reçues par un utilisateur
    List<Rating> findByReceiverId(Long receiverId);

    // Récupérer les évaluations postées par un utilisateur
    List<Rating> findByPosterId(Long posterId);
}



