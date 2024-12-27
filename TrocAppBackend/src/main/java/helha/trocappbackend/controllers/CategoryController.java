package helha.trocappbackend.controllers;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.repositories.UserRepository;
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

    /*@PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try {
            // Vérifiez si une catégorie avec le même nom existe déjà
            boolean categoryExists = categoryService.getAllCategories().stream()
                    .anyMatch(existingCategory ->
                            existingCategory.getName().equalsIgnoreCase(category.getName()));

            if (categoryExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A category with the same name already exists.");
            }

            // Sauvegarder la catégorie
            Category savedCategory = CategoryRepository.save(category);

            // Retourner la catégorie sauvegardée
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the category: " + e.getMessage());
        }
    }*/
    @PostMapping
    public ResponseEntity<Object> addCategory(@RequestBody Category category) {
        try {
            // Vérifiez si une catégorie avec le même nom existe déjà
            boolean categoryExists = categoryService.getAllCategories().stream()
                    .anyMatch(existingCategory ->
                            existingCategory.getName().equalsIgnoreCase(category.getName()));

            if (categoryExists) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A category with the same name already exists.");
            }

            // Sauvegarder la catégorie
            Category savedCategory = categoryService.createCategory(category);

            // Retourner la catégorie sauvegardée
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);

        } catch (Exception e) {
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