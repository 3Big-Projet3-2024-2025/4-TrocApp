package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// ASSADI DOHA
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository CategoryRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try {
            // Search for the administrator (user) by ID
            User user = userRepository.findById(category.getUser().getId())
                    .orElse(null); // Retourne null si l'administrateur n'est pas trouvé

            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Administrator not found with ID: " + category.getUser().getId());
            }
            //verify if the category already exists
            boolean categoryExists = categoryService.getAllCategories().stream()
                    .anyMatch(existingCategory ->
                            existingCategory.getName().equalsIgnoreCase(category.getName()) &&
                                    existingCategory.getUser().getId() == user.getId());

            if (categoryExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A category with the same name already exists for this administrator.");
            }
            // Assign the user (admin) to the category
            category.setUser(user);

            // Save the category
            Category savedCategory = CategoryRepository.save(category);

            // Return the saved category
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

        } catch (Exception e) {
            // Retourner un message d'erreur détaillé
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the category: " + e.getMessage());
        }
    }


    // update a category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    // delete a category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}