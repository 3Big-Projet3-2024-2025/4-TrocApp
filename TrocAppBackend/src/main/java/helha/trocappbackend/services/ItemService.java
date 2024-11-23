package helha.trocappbackend.services;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 972dc68 (Ajout de la classe ItemService)

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public Item getItem(int id) {
<<<<<<< HEAD
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null);
=======
        return itemRepository.getReferenceById(id);
>>>>>>> 972dc68 (Ajout de la classe ItemService)
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }
}
