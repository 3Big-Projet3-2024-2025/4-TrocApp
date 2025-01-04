package helha.trocappbackend.services;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing categories.
 * This class contains business logic for creating, updating, deleting, and retrieving categories.
 */
@Service
public class CategoryService {

    /**
     * Repository for accessing category data.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the database.
     *
     * @return List of all categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Creates a new category and saves it to the database.
     *
     * @param category The category to be created.
     * @return The saved category.
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Updates an existing category with the provided data.
     * If the category does not exist, an exception is thrown.
     *
     * @param id The ID of the category to update.
     * @param category The category data to update.
     * @return The updated category.
     * @throws RuntimeException if the category is not found.
     */
    public Category updateCategory(int id, Category category) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update category name if provided
        if (category.getName() != null) {
            existingCategory.setName(category.getName());
        }

        return categoryRepository.save(existingCategory);
    }

    /**
     * Deletes a category by its ID.
     * If the category contains items, the deletion is not allowed.
     *
     * @param id The ID of the category to delete.
     * @throws RuntimeException if the category is not found or contains items.
     */
    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Prevent deletion if the category contains items
        if (!category.getItems().isEmpty()) {
            throw new RuntimeException("Cannot delete category containing items");
        }

        categoryRepository.delete(category);
    }

    /**
     * Retrieves a category by its ID.
     * If the category is not found, an exception is thrown.
     *
     * @param id The ID of the category to retrieve.
     * @return The category with the specified ID.
     * @throws RuntimeException if the category is not found.
     */
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }
}
