package helha.trocappbackend.controllerTest;

import helha.trocappbackend.controllers.CategoryController;
import helha.trocappbackend.models.Category;
import helha.trocappbackend.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link CategoryController} class.
 * This class tests the various endpoints of the CategoryController,
 * ensuring that the logic for adding, updating, deleting, and fetching categories works as expected.
 */
public class CategoryControllerTest {

    /**
     * The {@link CategoryController} instance that is being tested.
     */
    @InjectMocks
    private CategoryController categoryController;

    /**
     * The mocked {@link CategoryService} instance that the controller depends on.
     */
    @Mock
    private CategoryService categoryService;

    /**
     * Setup method to initialize the mocks before each test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test the {@link CategoryController#getAllCategories()} method.
     *
     * @see CategoryController#getAllCategories()
     */
    @Test
    @DisplayName("Test Get All Categories - Success")
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Electronics"));
        categories.add(new Category(2, "Books"));

        when(categoryService.getAllCategories()).thenReturn(categories);

        List<Category> result = categoryController.getAllCategories();

        assertEquals(2, result.size());
        verify(categoryService, times(1)).getAllCategories();
    }

    /**
     * Test the {@link CategoryController#addCategory(Category)} method for a successful category creation.
     *
     * @see CategoryController#addCategory(Category)
     */
    @Test
    @DisplayName("Test Add Category - Success")
    public void testAddCategory_Success() {
        Category category = new Category(3, "Clothing");
        when(categoryService.getAllCategories()).thenReturn(new ArrayList<>());
        when(categoryService.createCategory(category)).thenReturn(category);

        ResponseEntity<Object> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).getAllCategories();
        verify(categoryService, times(1)).createCategory(category);
    }

    /**
     * Test the {@link CategoryController#addCategory(Category)} method for a conflict when the category already exists.
     *
     * @see CategoryController#addCategory(Category)
     */
    @Test
    @DisplayName("Test Add Category - Conflict (Category Already Exists)")
    public void testAddCategory_Conflict() {
        Category category = new Category(3, "Clothing");
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Clothing"));

        when(categoryService.getAllCategories()).thenReturn(categories);

        ResponseEntity<Object> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("A category with the same name already exists.", response.getBody());
        verify(categoryService, times(1)).getAllCategories();
        verify(categoryService, times(0)).createCategory(category);
    }

    /**
     * Test the {@link CategoryController#updateCategory(int, Category)} method for updating an existing category.
     *
     * @see CategoryController#updateCategory(int, Category)
     */
    @Test
    @DisplayName("Test Update Category - Success")
    public void testUpdateCategory() {
        Category category = new Category(1, "Updated Name");
        when(categoryService.updateCategory(1, category)).thenReturn(category);

        ResponseEntity<Category> response = categoryController.updateCategory(1, category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService, times(1)).updateCategory(1, category);
    }

    /**
     * Test the {@link CategoryController#deleteCategory(int)} method for successful category deletion.
     *
     * @see CategoryController#deleteCategory(int)
     */
    @Test
    @DisplayName("Test Delete Category - Success")
    public void testDeleteCategory_Success() {
        doNothing().when(categoryService).deleteCategory(1);

        ResponseEntity<String> response = categoryController.deleteCategory(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Category deleted successfully", response.getBody());
        verify(categoryService, times(1)).deleteCategory(1);
    }

    /**
     * Test the {@link CategoryController#deleteCategory(int)} method for failure when category is not found.
     *
     * @see CategoryController#deleteCategory(int)
     */
    @Test
    @DisplayName("Test Delete Category - Failure (Category Not Found)")
    public void testDeleteCategory_Failure() {
        doThrow(new RuntimeException("Category not found")).when(categoryService).deleteCategory(1);

        ResponseEntity<String> response = categoryController.deleteCategory(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Category not found", response.getBody());
        verify(categoryService, times(1)).deleteCategory(1);
    }
}
