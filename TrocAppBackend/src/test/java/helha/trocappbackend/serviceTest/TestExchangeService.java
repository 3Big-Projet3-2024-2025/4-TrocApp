package helha.trocappbackend.serviceTest;


import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.models.User;
import helha.trocappbackend.repositories.ExchangeRepository;
import helha.trocappbackend.services.ExchangeService;
import helha.trocappbackend.services.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link helha.trocappbackend.services.ExchangeService} class.
 *
 * <p>This class uses JUnit 5 and Mockito to test the functionality of the
 * {@link ExchangeService}. It validates the correct behavior of the service methods
 * by mocking the {@link helha.trocappbackend.repositories.ExchangeRepository}
 * and {@link helha.trocappbackend.services.ItemService}.</p>
 *
 * <p>Tests include scenarios for successful operations, error handling, and
 * boundary cases.</p>
 *
 * <p>Annotations used:</p>
 * <ul>
 *     <li>{@link SpringBootTest}: Indicates that this is a Spring Boot test.</li>
 *     <li>{@link Mock}: Creates mock objects for `ExchangeRepository` and `ItemService`.</li>
 *     <li>{@link InjectMocks}: Injects mocked dependencies into the `ExchangeService` instance.</li>
 *     <li>{@link BeforeEach}: Sets up the test data and initializes mocks before each test.</li>
 * </ul>
 * @author Hayriye Dogan
 * @see helha.trocappbackend.serviceTest
 */
@SpringBootTest
public class TestExchangeService {
    /**
     * Mocked instance of the {@link helha.trocappbackend.repositories.ExchangeRepository}.
     */
    @Mock
    private ExchangeRepository exchangeRepository;
    /**
     * Mocked instance of the {@link helha.trocappbackend.services.ItemService}.
     */
    @Mock
    private ItemService itemService;
    /**
     * Service under test.
     */
    @InjectMocks
    private ExchangeService exchangeService;
    /**
     * Test data objects.
     */
    private Exchange exchange1;
    private Exchange exchange2;
    private User initiator;
    private User receiver;
    private Item requestedItem;
    private Item offeredItem;

    /**
     * Initializes test data and mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initiator = new User();
        initiator.setId(1);

        receiver = new User();
        receiver.setId(2);

        requestedItem = new Item();
        requestedItem.setId(1);
        requestedItem.setAvailable(true);

        offeredItem = new Item();
        offeredItem.setId(2);
        offeredItem.setAvailable(true);

        exchange1 = new Exchange();
        exchange1.setId_exchange(1);
        exchange1.setInitiator(initiator);
        exchange1.setReceiver(receiver);
        exchange1.setRequestedObjectId(requestedItem.getId());
        exchange1.setOfferedObjectId(offeredItem.getId());

        exchange2 = new Exchange();
        exchange2.setId_exchange(2);
        exchange2.setInitiator(receiver);
        exchange2.setReceiver(initiator);
        exchange2.setRequestedObjectId(offeredItem.getId());
        exchange2.setOfferedObjectId(requestedItem.getId());
    }

    /**
     * Tests adding an exchange successfully.
     */
    @Test
    @DisplayName("Add an exchange successfully")
    void addExchange_Success() {
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange1);

        Exchange result = exchangeService.addExchange(exchange1);

        assertNotNull(result);
        assertEquals(exchange1.getId_exchange(), result.getId_exchange());
        verify(exchangeRepository, times(1)).save(any(Exchange.class));
    }

    /**
     * Tests adding an exchange when the repository throws an exception.
     */
    @Test
    @DisplayName("Add an exchange throws an exception")
    void addExchange_ThrowsException() {
        when(exchangeRepository.save(any(Exchange.class)))
                .thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> exchangeService.addExchange(exchange1));
    }

    /**
     * Tests retrieving all exchanges successfully.
     */
    @Test
    @DisplayName("Retrieve all exchanges successfully")
    void getAllExchanges_Success() {
        when(exchangeRepository.findAll()).thenReturn(Arrays.asList(exchange1, exchange2));

        List<Exchange> result = exchangeService.getAllExchanges();

        assertEquals(2, result.size());
        verify(exchangeRepository, times(1)).findAll();
    }

    /**
     * Tests retrieving an exchange by ID successfully.
     */
    @Test
    @DisplayName("Retrieve an exchange by ID successfully")
    void getExchangeById_Success() {
        when(exchangeRepository.findById(1)).thenReturn(Optional.of(exchange1));

        Exchange result = exchangeService.getExchangeById(1);

        assertNotNull(result);
        assertEquals(1, result.getId_exchange());
    }

    /**
     * Tests retrieving an exchange by an ID that does not exist.
     */
    @Test
    @DisplayName("Retrieve an exchange by ID not found")
    void getExchangeById_NotFound() {
        when(exchangeRepository.findById(999)).thenReturn(Optional.empty());

        Exchange result = exchangeService.getExchangeById(999);

        assertNull(result);
    }

    /**
     * Tests retrieving all exchanges for a user by their ID.
     */
    @Test
    @DisplayName("Retrieve all exchanges for a user as initiator")
    void getAllExchangesByUserID_AsInitiator() {
        when(exchangeRepository.findAll()).thenReturn(Arrays.asList(exchange1, exchange2));

        List<Exchange> result = exchangeService.getAllExchangesByUserID(1);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getInitiator().getId() == 1));
        assertTrue(result.stream().anyMatch(e -> e.getReceiver().getId() == 1));
    }

    /**
     * Tests deleting an exchange by ID successfully.
     */
    @Test
    @DisplayName("Delete an exchange by ID successfully")
    void deleteExchangeById_Success() {
        doNothing().when(exchangeRepository).deleteById(1);

        assertDoesNotThrow(() -> exchangeService.deleteExchangeById(1));
        verify(exchangeRepository, times(1)).deleteById(1);
    }

    /**
     * Tests deleting an exchange by ID when an exception is thrown.
     */
    @Test
    @DisplayName("Delete an exchange by ID throws an exception")
    void deleteExchangeById_ThrowsException() {
        doThrow(new RuntimeException("Delete error"))
                .when(exchangeRepository).deleteById(999);

        assertThrows(RuntimeException.class, () -> exchangeService.deleteExchangeById(999));
    }

    /**
     * Tests updating an exchange successfully and marking items as unavailable when accepted.
     */
    @Test
    @DisplayName("Update an exchange successfully with accepted true")
    void updateExchange_SuccessWithAcceptedTrue() {
        when(exchangeRepository.existsById(1)).thenReturn(true);
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange1);
        when(itemService.getItemById(anyInt())).thenReturn(requestedItem, offeredItem);

        Exchange result = exchangeService.updateExchange(exchange1, true);

        assertNotNull(result);
        verify(exchangeRepository).save(exchange1);
        verify(itemService, times(2)).getItemById(anyInt());
        assertFalse(requestedItem.isAvailable());
        assertFalse(offeredItem.isAvailable());
    }

    /**
     * Tests updating an exchange successfully without modifying items when not accepted.
     */
    @Test
    @DisplayName("Update an exchange successfully with accepted false")
    void updateExchange_SuccessWithAcceptedFalse() {
        when(exchangeRepository.existsById(1)).thenReturn(true);
        when(exchangeRepository.save(any(Exchange.class))).thenReturn(exchange1);

        Exchange result = exchangeService.updateExchange(exchange1, false);

        assertNotNull(result);
        verify(exchangeRepository).save(exchange1);
        verify(itemService, never()).getItemById(anyInt());
    }

    /**
     * Tests updating an exchange when the exchange does not exist.
     */
    @Test
    @DisplayName("Update an exchange not found")
    void updateExchange_NotFound() {
        when(exchangeRepository.existsById(999)).thenReturn(false);

        Exchange invalidExchange = new Exchange();
        invalidExchange.setId_exchange(999);

        assertThrows(RuntimeException.class,
                () -> exchangeService.updateExchange(invalidExchange, true));
    }

    /**
     * Tests updating an exchange when the repository throws an exception.
     */
    @Test
    @DisplayName("Update an exchange throws an exception")
    void updateExchange_ThrowsException() {
        when(exchangeRepository.existsById(1)).thenReturn(true);
        when(exchangeRepository.save(any(Exchange.class)))
                .thenThrow(new RuntimeException("Update error"));

        assertThrows(RuntimeException.class,
                () -> exchangeService.updateExchange(exchange1, false));
    }
}
