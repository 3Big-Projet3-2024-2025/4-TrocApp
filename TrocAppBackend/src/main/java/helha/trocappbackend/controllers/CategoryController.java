package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.repositories.ItemRepository;
import helha.trocappbackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing categories.
 * Provides endpoints for adding, updating, deleting, and retrieving categories.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    /**
     * Service for managing categories.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * Repository for accessing category data.
     */
    @Autowired
    private CategoryRepository CategoryRepository;

    /**
     * Repository for accessing item data.
     */
    @Autowired
    private ItemRepository itemRepository;

    /**
     * Endpoint for retrieving all categories.
     *
     * @return List of all categories.
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Endpoint for adding a new category.
     * Verifies if the category already exists by name before saving.
     *
     * @param category The category object to be added.
     * @return ResponseEntity containing the created category or an error message.
     */
    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try {
            // Check if the category already exists
            boolean categoryExists = categoryService.getAllCategories().stream()
                    .anyMatch(existingCategory -> existingCategory.getName().equalsIgnoreCase(category.getName()));

            if (categoryExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A category with the same name already exists.");
            }

            // Save the category
            Category savedCategory = categoryService.createCategory(category);

            // Return the saved category
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the category: " + e.getMessage());
        }
    }

    /**
     * Endpoint for updating an existing category by ID.
     *
     * @param id The ID of the category to update.
     * @param category The category object containing updated data.
     * @return ResponseEntity with the updated category.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    /**
     * Endpoint for deleting a category by ID.
     *
     * @param id The ID of the category to delete.
     * @return ResponseEntity with a success or error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint for retrieving a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return ResponseEntity containing the category or a not-found error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        try {
            Category category = categoryService.getCategoryById(id);
            if (category == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
            return ResponseEntity.ok(category);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
