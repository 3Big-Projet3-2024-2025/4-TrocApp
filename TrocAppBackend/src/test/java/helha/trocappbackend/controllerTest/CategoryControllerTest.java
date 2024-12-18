package helha.trocappbackend.controllerTest;

import helha.trocappbackend.controllers.CategoryController;
import helha.trocappbackend.models.Category;
import helha.trocappbackend.models.User;
import helha.trocappbackend.services.CategoryService;
import helha.trocappbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

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

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(category1, category2));

        // Act
        List<Category> categories = categoryController.getAllCategories();

        // Assert
        assertEquals(2, categories.size());
        assertEquals("Books", categories.get(0).getName());
        assertEquals("Electronics", categories.get(1).getName());
        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    void testAddCategory_Success() {
        // Arrange
        User admin = new User();
        admin.setId(1);
        admin.setFirstName("Admin");

        Category category = new Category();
        category.setName("Dvd");
        category.setUser(admin);

        when(userRepository.findById(1)).thenReturn(Optional.of(admin));
        when(categoryService.createCategory(any(Category.class))).thenReturn(category);

        // Act
        ResponseEntity<Object> response = categoryController.addCategory(category);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Category);
        assertEquals("Dvd", ((Category) response.getBody()).getName());
    }

    @Test
    void testAddCategory_AdminNotFound() {
        // Arrange
        User admin = new User();
        admin.setId(99); // Non-existent admin ID

        Category category = new Category();
        category.setName("Dvd");
        category.setUser(admin);

        when(userRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Object> response = categoryController.addCategory(category);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
        assertTrue(response.getBody().toString().contains("Administrator not found"));
    }

    @Test
    void testUpdateCategory() {
        // Arrange
        int categoryId = 1;
        Category existingCategory = new Category();
        existingCategory.setId_category(categoryId);
        existingCategory.setName("Dvd");

        Category updatedCategory = new Category();
        updatedCategory.setName("Updated Dvd");

        when(categoryService.updateCategory(categoryId, updatedCategory)).thenReturn(updatedCategory);

        // Act
        ResponseEntity<Category> response = categoryController.updateCategory(categoryId, updatedCategory);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Dvd", response.getBody().getName());
        verify(categoryService, times(1)).updateCategory(categoryId, updatedCategory);
    }

    @Test
    void testDeleteCategory_Success() {
        // Arrange
        int categoryId = 1;

        doNothing().when(categoryService).deleteCategory(categoryId);

        // Act
        ResponseEntity<String> response = categoryController.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }

    @Test
    void testDeleteCategory_Failure() {
        // Arrange
        int categoryId = 1;
        doThrow(new RuntimeException("Category not found")).when(categoryService).deleteCategory(categoryId);

        // Act
        ResponseEntity<String> response = categoryController.deleteCategory(categoryId);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Category not found", response.getBody());
        verify(categoryService, times(1)).deleteCategory(categoryId);
    }
}


