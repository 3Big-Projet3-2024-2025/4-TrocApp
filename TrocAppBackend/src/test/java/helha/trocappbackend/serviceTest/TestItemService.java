package helha.trocappbackend.serviceTest;

import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.ItemRepository;
import helha.trocappbackend.services.ItemService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link helha.trocappbackend.services.ItemService} class.
 *
 * <p>This test class uses JUnit 5 and Mockito to perform unit tests on various
 * methods of the {@link ItemService}. It verifies the behavior of the service
 * methods by mocking the {@link helha.trocappbackend.repositories.ItemRepository}.</p>
 *
 * <p>Each test ensures proper functionality of a specific service method,
 * including successful cases, edge cases, and exception handling scenarios.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *     <li>{@link SpringBootTest}: Used to indicate that the test is a Spring Boot test.</li>
 *     <li>{@link Mock}: Used to create a mock object for the `ItemRepository`.</li>
 *     <li>{@link InjectMocks}: Injects the mock dependencies into the `ItemService` instance.</li>
 *     <li>{@link BeforeEach}: Sets up test data and initializes mocks before each test case.</li>
 * </ul>
 * @author Hayriye Dogan
 * @see helha.trocappbackend.serviceTest
 */
@SpringBootTest
public class TestItemService {
    /**
     * Mocked instance of the {@link helha.trocappbackend.repositories.ItemRepository}.
     */
    @Mock
    private ItemRepository itemRepository;

    /**
     * Service under test.
     */
    @InjectMocks
    private ItemService itemService;

    /**
     * Sample data for testing.
     */
    private Item item1;
    private Item item2;
    private User owner;

    /**
     * Sets up sample data and initializes mocks before each test case.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        owner = new User();
        owner.setId(1);

        item1 = new Item();
        item1.setId(1);
        item1.setAvailable(true);
        item1.setOwner(owner);

        item2 = new Item();
        item2.setId(2);
        item2.setAvailable(false);
        item2.setOwner(owner);
    }

    /**
     * Tests the successful addition of an item.
     */
    @Test
    @DisplayName("Add an item successfully")
    void addItem_Success() {
        when(itemRepository.save(any(Item.class))).thenReturn(item1);

        Item result = itemService.addItem(item1);

        assertNotNull(result);
        assertEquals(item1.getId(), result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    /**
     * Tests the addition of an item when the repository throws an exception.
     */
    @Test
    @DisplayName("Add an item throws an exception")
    void addItem_ThrowsException() {
        when(itemRepository.save(any(Item.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> itemService.addItem(item1));
    }

    /**
     * Tests retrieving an item by its ID successfully.
     */
    @Test
    @DisplayName("Get an item by ID successfully")
    void getItemById_Success() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(item1));

        Item result = itemService.getItemById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    /**
     * Tests retrieving an item by an ID that does not exist.
     */
    @Test
    @DisplayName("Get an item by ID that does not exist")
    void getItemById_NotFound() {
        when(itemRepository.findById(999)).thenReturn(Optional.empty());

        Item result = itemService.getItemById(999);

        assertNull(result);
    }

    /**
     * Tests retrieving all available items successfully.
     */
    @Test
    @DisplayName("Get all available items successfully")
    void getAllAvailableItems_Success() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.getAllAvailableItems();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    /**
     * Tests retrieving all items successfully.
     */
    @Test
    @DisplayName("Get all items successfully")
    void getAllItems_Success() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.getAllItems();

        assertEquals(2, result.size());
    }

    /**
     * Tests retrieving all available items by user ID successfully.
     */
    @Test
    @DisplayName("Get all available items by user ID successfully")
    void getAllAvailableItemsByUserId_Success() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.getAllAvailableItemsByUserId(1);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
        assertEquals(1, result.get(0).getOwner().getId());
    }

    /**
     * Tests deleting an item successfully.
     */
    @Test
    @DisplayName("Delete an item successfully")
    void deleteItem_Success() {
        doNothing().when(itemRepository).deleteById(1);

        assertDoesNotThrow(() -> itemService.deleteItem(1));
        verify(itemRepository, times(1)).deleteById(1);
    }

    /**
     * Tests deleting an item when the repository throws an exception.
     */
    @Test
    @DisplayName("Delete an item throws an exception")
    void deleteItem_ThrowsException() {
        doThrow(new RuntimeException("Delete error")).when(itemRepository).deleteById(999);

        assertThrows(RuntimeException.class, () -> itemService.deleteItem(999));
    }

    /**
     * Tests updating an item successfully.
     */
    @Test
    @DisplayName("Update an item successfully")
    void updateItem_Success() {
        when(itemRepository.existsById(1)).thenReturn(true);
        when(itemRepository.save(any(Item.class))).thenReturn(item1);

        Item result = itemService.updateItem(item1);

        assertNotNull(result);
        assertEquals(item1.getId(), result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    /**
     * Tests updating an item that does not exist.
     */
    @Test
    @DisplayName("Update an item that does not exist")
    void updateItem_NotFound() {
        when(itemRepository.existsById(999)).thenReturn(false);

        Item invalidItem = new Item();
        invalidItem.setId(999);

        assertThrows(RuntimeException.class, () -> itemService.updateItem(invalidItem));
    }
}
