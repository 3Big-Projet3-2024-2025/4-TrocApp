package helha.trocappbackend.services;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> 972dc68 (Ajout de la classe ItemService)
=======
import java.util.Optional;
>>>>>>> aab4377 (Correction bug pour getItemById (itemService.java, item.java))

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item) {
<<<<<<< HEAD
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException("Ajout de item a echoué " + e.getMessage());
        }
    }

    public Item getItem(int id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            return item.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Get de item a echoué " + e.getMessage());
        }
    }

    public List<Item> getAllItems() {
        try {
            return itemRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get des items a echoué " + e.getMessage());
        }
    }

    public void deleteItem(int id) {
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Suppression de item a echoué " + e.getMessage());
        }
    }

    public Item updateItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException("La mise a jour de item a echoué " + e.getMessage());
        }
=======
        return itemRepository.save(item);
    }

    public Item getItem(int id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null);
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Item item) {
        return itemRepository.save(item);
>>>>>>> 972dc68 (Ajout de la classe ItemService)
    }
}
