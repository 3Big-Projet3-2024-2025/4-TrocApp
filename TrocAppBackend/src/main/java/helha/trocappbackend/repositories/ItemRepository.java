package helha.trocappbackend.repositories;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByUser(User user);
    // Optionnellement, vous pouvez ajouter une méthode pour trier par date
    List<Item> findByUserOrderByCreatedDateDesc(User user);
}
