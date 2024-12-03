package helha.trocappbackend.serviceTest;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = Arrays.asList(
                new Category(1, "Category 1"),
                new Category(2, "Category 2")
        );

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();
        assertEquals(2, result.size());
        assertEquals("Category 1", result.get(0).getName());
        assertEquals("Category 2", result.get(1).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category(1, "New Category");

        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.createCategory(new Category("New Category"));
        assertEquals("New Category", result.getName());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testUpdateCategory() {
        Category existingCategory = new Category(1, "Existing Category");
        Category updatedCategory = new Category(1, "Updated Category");

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.updateCategory(1, new Category("Updated Category"));
        assertEquals("Updated Category", result.getName());

        verify(categoryRepository, times(1)).findById(anyInt());
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    public void testUpdateCategoryNotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(1, new Category("Updated Category"));
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(anyInt());
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category(1, "Category to delete");
        category.setItems(Arrays.asList()); // Empty items list

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(any(Category.class));

        categoryService.deleteCategory(1);

        verify(categoryRepository, times(1)).findById(anyInt());
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testDeleteCategoryNotFound() {
        when(categoryRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(1);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(anyInt());
        verify(categoryRepository, times(0)).delete(any(Category.class));
    }

    @Test
    public void testDeleteCategoryContainingItems() {
        Category category = new Category(1, "Category with items");
        category.setItems(Arrays.asList(new Item())); // Non-empty items list

        when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(category));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(1);
        });

        assertEquals("Cannot delete category containing items", exception.getMessage());
        verify(categoryRepository, times(1)).findById(anyInt());
        verify(categoryRepository, times(0)).delete(any(Category.class));
    }
}
