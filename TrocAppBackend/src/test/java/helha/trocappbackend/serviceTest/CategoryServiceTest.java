
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Electronics"));
        categories.add(new Category(2, "Books"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Books", result.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category(3, "Clothing");
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertEquals("Clothing", result.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testUpdateCategory_Success() {
        Category existingCategory = new Category(1, "Electronics");
        Category updatedCategory = new Category("Gadgets");

        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category result = categoryService.updateCategory(1, updatedCategory);

        assertEquals("Gadgets", result.getName());
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    public void testUpdateCategory_NotFound() {
        Category updatedCategory = new Category("Gadgets");

        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(1, updatedCategory);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(0)).save(any(Category.class));
    }

    @Test
    public void testDeleteCategory_Success() {
        Category category = new Category(1, "Electronics");
        category.setItems(new ArrayList<>());

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);

        categoryService.deleteCategory(1);

        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    public void testDeleteCategory_NotFound() {
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(1);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(0)).delete(any(Category.class));
    }

    @Test
    public void testDeleteCategory_WithItems() {
        Category category = new Category(1, "Electronics");
        List<Item> items = new ArrayList<>();
        items.add(new Item());
        category.setItems(items);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(1);
        });

        assertEquals("Cannot delete category containing items", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1);
        verify(categoryRepository, times(0)).delete(any(Category.class));
    }
}
