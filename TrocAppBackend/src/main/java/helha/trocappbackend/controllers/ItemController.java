package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.services.EmailService;
import helha.trocappbackend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing `Item` resources.
 *
 * <p>This controller handles HTTP requests for operations related to items,
 * such as adding, retrieving, updating, and deleting items. It also supports
 * filtering items by availability and retrieving items associated with a specific user.</p>
 *
 * <p>Endpoints are exposed at `/items` and support cross-origin requests from `http://localhost:4200`.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * // Add a new item
 * POST /items
 *
 * // Get all items
 * GET /items
 * }</pre>
 *
 *  @author Hayriye Dogan
 *  @see helha.trocappbackend.controllers
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/items")
public class ItemController {
    /**
     * Service for managing `Item` entities.
     */
    @Autowired
    private ItemService itemService;
    /**
     * Repository for accessing `Category` data.
     */
    @Autowired
    private CategoryRepository categoryRepository;
    /**
     * Service for sending email notifications.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Adds a new `Item` to the database and sends a confirmation email to the item's owner.
     *         we find the given category and we store it in the item,
     *         so the returned item will have a category that already exists
     * @param item The `Item` object to be added.
     * @return A `ResponseEntity` containing the added `Item` or a `400 Bad Request` status if the operation fails.
     */
    @PostMapping
    public ResponseEntity<?> addItem(@RequestBody Item item) {
        try {
            Integer idCategory = item.getCategory().getId_category();
            Category category = categoryRepository.findById(idCategory)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            item.setCategory(category);
            Item newItem = itemService.addItem(item);

            String emailOwner = newItem.getOwner().getEmail();
            String nameOwner = newItem.getOwner().getFirstName() + " " + newItem.getOwner().getLastName();
            String subjectMail = "Creation of an Item";
            String bodyMail = "Hello, "+ nameOwner +" we confirm your item was succesfully created";

            emailService.sendEmail(emailOwner, subjectMail, bodyMail);
            return ResponseEntity.ok(newItem);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);  //badRequest  entree invalide
        }
    }

    /**
     * Retrieves all items from the database.
     *
     * @return A `ResponseEntity` containing the list of all items or a `500 Internal Server Error` status if the operation fails.
     */
    @GetMapping()
    public ResponseEntity<List<Item>> getAllItems() {
        try {
            List<Item> items = itemService.getAllItems();
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);    //err cote serveur exc java
        }
    }

    /**
     * Retrieves all available items.
     *
     * @return A `ResponseEntity` containing the list of available items or a `500 Internal Server Error` status if the operation fails.
     */
    @GetMapping("/available")
    public ResponseEntity<List<Item>> getAllAvailableItems() {
        try {
            List<Item> items = itemService.getAllAvailableItems();
            return ResponseEntity.ok(items);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);    //err cote serveur exc java
        }
    }

    /**
     * Retrieves all available items owned by a specific user.
     *
     * @param id The ID of the user.
     * @return A `ResponseEntity` containing the list of items owned by the user or a `500 Internal Server Error` status if the operation fails.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Item>> getAllItemsByUserID(@PathVariable int id) {
        try {
            List<Item> itemsOfUserID = itemService.getAllAvailableItemsByUserId(id);
            return ResponseEntity.ok(itemsOfUserID);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Deletes an item by its ID.
     *
     * @param id The ID of the item to delete.
     * @return A `204 No Content` response if the deletion is successful or a `500 Internal Server Error` status if the operation fails.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();                  //no content au lieu de ok(renvoye)
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);    //  build  au lieu de body null ?
        }
    }
    /**
     * Updates an existing item.
     *
     * @param id   The ID of the item to update.
     * @param item The updated `Item` object.
     * @return A `ResponseEntity` containing the updated `Item` or a `400 Bad Request` status if the operation fails.
     */

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item item) {
        try {
            item.setId(id);
            Item itemUpdated = itemService.updateItem(item);
            return ResponseEntity.ok(itemUpdated);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);       ///// badRequest
        }
    }

    /**
     * Retrieves an item by its ID.
     *
     * @param id The ID of the item.
     * @return The `Item` object or `null` if not found.
     */
    @GetMapping("/{id}")
    public Item getItem(@PathVariable("id") int id) {
        return itemService.getItemById(id);
    }
}




