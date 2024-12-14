package helha.trocappbackend.services;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException("Adding item failed " + e.getMessage());
        }
    }

    public Item getItemById(int id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            return item.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Retrieving item failed " + e.getMessage());
        }
    }

    public List<Item> getAllAvailableItems() {
        try {
            return itemRepository.findAll().stream().filter(Item::isAvailable).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Retrieving available items failed " + e.getMessage());
        }
    }

    public List<Item> getAllItems() {
        try {
            return itemRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieving all items failed " + e.getMessage());
        }
    }

    public void deleteItem(int id) {
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Deletion of item failed " + e.getMessage());
        }
    }

    public Item updateItem(Item item) {
        try {
            if (!itemRepository.existsById(item.getId())) {
                throw new RuntimeException("Item not found with ID: " + item.getId());
            }
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException("Update of item failed" + e.getMessage());
        }
    }
}
