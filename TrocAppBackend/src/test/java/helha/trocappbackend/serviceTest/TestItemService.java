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

@SpringBootTest
public class TestItemService {
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    private Item item1;
    private Item item2;
    private User owner;

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

    @Test
    @DisplayName("Add an item successfully")
    void addItem_Success() {
        when(itemRepository.save(any(Item.class))).thenReturn(item1);

        Item result = itemService.addItem(item1);

        assertNotNull(result);
        assertEquals(item1.getId(), result.getId());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    @DisplayName("Add an item throws an exception")
    void addItem_ThrowsException() {
        when(itemRepository.save(any(Item.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> itemService.addItem(item1));
    }

    @Test
    @DisplayName("Get an item by ID successfully")
    void getItemById_Success() {
        when(itemRepository.findById(1)).thenReturn(Optional.of(item1));

        Item result = itemService.getItemById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Get an item by ID that does not exist")
    void getItemById_NotFound() {
        when(itemRepository.findById(999)).thenReturn(Optional.empty());

        Item result = itemService.getItemById(999);

        assertNull(result);
    }

    @Test
    @DisplayName("Get all available items successfully")
    void getAllAvailableItems_Success() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.getAllAvailableItems();

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
    }

    @Test
    @DisplayName("Get all items successfully")
    void getAllItems_Success() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.getAllItems();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Get all available items by user ID successfully")
    void getAllAvailableItemsByUserId_Success() {
        when(itemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<Item> result = itemService.getAllAvailableItemsByUserId(1);

        assertEquals(1, result.size());
        assertTrue(result.get(0).isAvailable());
        assertEquals(1, result.get(0).getOwner().getId());
    }

    @Test
    @DisplayName("Delete an item successfully")
    void deleteItem_Success() {
        doNothing().when(itemRepository).deleteById(1);

        assertDoesNotThrow(() -> itemService.deleteItem(1));
        verify(itemRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Delete an item throws an exception")
    void deleteItem_ThrowsException() {
        doThrow(new RuntimeException("Delete error")).when(itemRepository).deleteById(999);

        assertThrows(RuntimeException.class, () -> itemService.deleteItem(999));
    }

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

    @Test
    @DisplayName("Update an item that does not exist")
    void updateItem_NotFound() {
        when(itemRepository.existsById(999)).thenReturn(false);

        Item invalidItem = new Item();
        invalidItem.setId(999);

        assertThrows(RuntimeException.class, () -> itemService.updateItem(invalidItem));
    }
}
