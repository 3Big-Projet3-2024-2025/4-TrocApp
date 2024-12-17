
package helha.trocappbackend.serviceTest;

import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.CategoryRepository;
import helha.trocappbackend.repositories.UserRepository;
import helha.trocappbackend.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        Category category1 = new Category();
        category1.setId_category(1);
        category1.setName("Books");

        Category category2 = new Category();
        category2.setId_category(2);
        category2.setName("Electronics");

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<Category> categories = categoryService.getAllCategories();

        // Assert
        assertEquals(2, categories.size());
        assertEquals("Books", categories.get(0).getName());
        assertEquals("Electronics", categories.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testCreateCategory_Success() {
        // Arrange
        User user = new User();
        user.setId(1);

        Category category = new Category();
        category.setName("Books");
        category.setUser(user);

        when(userRepository.existsById(1)).thenReturn(true);
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Category createdCategory = categoryService.createCategory(category);

        // Assert
        assertNotNull(createdCategory);
        assertEquals("Books", createdCategory.getName());
        verify(userRepository, times(1)).existsById(1);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testCreateCategory_InvalidUser() {
        // Arrange
        User user = new User();
        user.setId(99); // Non-existent user ID

        Category category = new Category();
        category.setName("Books");
        category.setUser(user);

        when(userRepository.existsById(99)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.createCategory(category);
        });

        assertEquals("Invalid user ID", exception.getMessage());
        verify(userRepository, times(1)).existsById(99);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_Success() {
        // Arrange
        int categoryId = 1;

        Category existingCategory = new Category();
        existingCategory.setId_category(categoryId);
        existingCategory.setName("Books");

        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Books");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        // Act
        Category result = categoryService.updateCategory(categoryId, updatedCategory);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Books", result.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testUpdateCategory_NotFound() {
        // Arrange
        int categoryId = 99;

        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Books");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(categoryId, updatedCategory);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testDeleteCategory_Success() {
        // Arrange
        int categoryId = 1;

        Category category = new Category();
        category.setId_category(categoryId);
        category.setItems(Collections.emptyList());

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        categoryService.deleteCategory(categoryId);

        // Assert
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }


    @Test
    void testDeleteCategory_WithItems() {
        // Arrange
        int categoryId = 1;

        Category category = new Category();
        category.setId_category(categoryId);

        // Simulating items in the category
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Item1");

        Item item2 = new Item();
        item2.setId(2);
        item2.setName("Item2");

        category.setItems(Arrays.asList(item1, item2)); // Setting a list of Item objects

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });

        assertEquals("Cannot delete category containing items", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any(Category.class));
    }


    @Test
    void testDeleteCategory_NotFound() {
        // Arrange
        int categoryId = 99;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });

        assertEquals("Category not found", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any(Category.class));
    }
}

