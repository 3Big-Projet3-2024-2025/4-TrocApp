package helha.trocappbackend.services;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(int id, Category category) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(category.getName());
        return categoryRepository.save(existingCategory);
    }

    /*public void deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        if (!category.getItems().isEmpty()) {
            throw new RuntimeException("Cannot delete category containing items");
        }
        categoryRepository.delete(category);
    }*/
}