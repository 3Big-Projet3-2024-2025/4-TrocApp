package helha.trocappbackend.services;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service class for managing `Item` entities.
 *
 * <p>This class provides various methods to perform CRUD operations and
 * custom queries related to `Item` objects.</p>
 *
 * <p>All methods handle exceptions and wrap them in `RuntimeException`
 * with a descriptive message.</p>
 *
 *  @author Hayriye Dogan
 *  @see helha.trocappbackend.services
 */
@Service
public class ItemService {
    /**
     * Repository for performing database operations on `Item` entities.
     * This is auto-wired by Spring.
     */
    @Autowired
    private ItemRepository itemRepository;

    /**
     * Adds a new `Item` to the database.
     *
     * @param item The `Item` object to be added.
     * @return The saved `Item` object.
     * @throws RuntimeException If an error occurs while saving the item.
     */
    public Item addItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RuntimeException("Adding item failed " + e.getMessage());
        }
    }

    /**
     * Retrieves an `Item` by its ID.
     *
     * @param id The ID of the `Item` to retrieve.
     * @return The `Item` object, or `null` if not found.
     * @throws RuntimeException If an error occurs while retrieving the item.
     */
    public Item getItemById(int id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            return item.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Retrieving item failed " + e.getMessage());
        }
    }

    /**
     * Retrieves all available `Item` objects.
     *
     * @return A list of available `Item` objects.
     * @throws RuntimeException If an error occurs while retrieving items.
     */
    public List<Item> getAllAvailableItems() {
        try {
            return itemRepository.findAll().stream().filter(Item::isAvailable).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Retrieving available items failed " + e.getMessage());
        }
    }

    /**
     * Retrieves all `Item` objects including the non-available.
     *
     * @return A list of all `Item` objects.
     * @throws RuntimeException If an error occurs while retrieving items.
     */
    public List<Item> getAllItems() {
        try {
            return itemRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieving all items failed " + e.getMessage());
        }
    }

    /**
     * Retrieves all available `Item` objects owned by a specific user.
     *
     * @param userId The ID of the user whose items to retrieve.
     * @return A list of available `Item` objects owned by the user.
     * @throws RuntimeException If an error occurs while retrieving items.
     */
    public List<Item> getAllAvailableItemsByUserId(int userId) {
        try {
            return itemRepository.findAll().stream().filter(item -> item.getOwner().getId() == userId && item.isAvailable()).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Retrieving all my items failed " + e.getMessage());
        }
    }

    /**
     * Deletes an `Item` by its ID.
     *
     * @param id The ID of the `Item` to delete.
     * @throws RuntimeException If an error occurs during deletion.
     */
    public void deleteItem(int id) {
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Deletion of item failed " + e.getMessage());
        }
    }

    /**
     * Updates an existing `Item` in the database.
     *
     * @param item The updated `Item` object.
     * @return The saved `Item` object.
     * @throws RuntimeException If the `Item` does not exist or an error occurs during the update.
     */
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
