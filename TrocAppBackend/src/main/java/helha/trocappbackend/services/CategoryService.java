package helha.trocappbackend.services;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        if (category.getUser() == null ||
                category.getUser().getId() == 0 ||
                !userRepository.existsById(category.getUser().getId())) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        return categoryRepository.save(category);
    }

    public Category updateCategory(int id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        if (category.getName() != null) {
            existingCategory.setName(category.getName());
        }

        if (category.getUser() != null &&
                userRepository.existsById(category.getUser().getId())) {
            existingCategory.setUser(category.getUser());
        } else if (category.getUser() != null) {
            throw new IllegalArgumentException("Invalid user ID");
        }        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (!category.getItems().isEmpty()) {
            throw new RuntimeException("Cannot delete category containing items");
        }
        categoryRepository.delete(category);
    }
}