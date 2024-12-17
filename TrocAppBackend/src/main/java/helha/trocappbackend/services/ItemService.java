package helha.trocappbackend.services;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item) {
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

    public List<Item> getAllAvailableItemsByUserId(int userId) {
        try {
            return itemRepository.findAll().stream().filter(item -> item.getOwner().getId() == userId && item.isAvailable()).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Retrieving all my items failed " + e.getMessage());
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
    }
}
