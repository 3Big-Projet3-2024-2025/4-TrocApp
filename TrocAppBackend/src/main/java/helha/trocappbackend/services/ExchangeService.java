package helha.trocappbackend.services;

import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing `Exchange` entities.
 *
 * <p>This class provides methods to handle the creation, retrieval, update,
 * and deletion of exchanges between users, as well as updating the availability
 * of items involved in exchanges.</p>
 *
 *  @author Hayriye Dogan
 *  @see helha.trocappbackend.services */
@Service
public class ExchangeService {
    /**
     * Repository for performing database operations on `Exchange` entities.
     */
    @Autowired
    private ExchangeRepository exchangeRepository;
    /**
     * Service for managing `Item` entities, used to update item availability.
     */
    @Autowired
    private ItemService itemService;

    /**
     * Adds a new `Exchange` to the database.
     *
     * @param exchange The `Exchange` object to be added.
     * @return The saved `Exchange` object.
     * @throws RuntimeException If an error occurs during the save operation.
     */
    public Exchange addExchange(Exchange exchange) {
        try {
            return exchangeRepository.save(exchange);
        } catch (Exception e) {
            throw new RuntimeException("Adding an Exchange failed " + e.getMessage());
        }
    }

    /**
     * Retrieves all `Exchange` objects from the database.
     *
     * @return A list of all `Exchange` objects.
     * @throws RuntimeException If an error occurs during the retrieval operation.
     */
    public List<Exchange> getAllExchanges() {
        try {
            return exchangeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get of all Exchanges failed " + e.getMessage());
        }
    }

    /**
     * Retrieves a specific `Exchange` by its ID.
     *
     * @param id The ID of the `Exchange` to retrieve.
     * @return The `Exchange` object, or `null` if not found.
     * @throws RuntimeException If an error occurs during the retrieval operation.
     */
    public Exchange getExchangeById(int id) {
        try {
            Optional<Exchange> exchange = exchangeRepository.findById(id);
            return exchange.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Get of an Exchange failed " + e.getMessage());
        }
    }

    /**
     * Retrieves all `Exchange` objects associated with a specific user.
     *
     * @param userID The ID of the user whose exchanges to retrieve.
     * @return A list of `Exchange` objects involving the specified user.
     * @throws RuntimeException If an error occurs during the retrieval operation.
     */
    public List<Exchange> getAllExchangesByUserID(int userID) {
        try {
            return exchangeRepository.findAll().stream().filter(exchange -> exchange.getInitiator().getId()==userID | exchange.getReceiver().getId()==userID).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Get of all My Exchanges failed " + e.getMessage());
        }
    }

    /**
     * Deletes an `Exchange` by its ID.
     *
     * @param id The ID of the `Exchange` to delete.
     * @throws RuntimeException If an error occurs during the deletion operation.
     */
    public void deleteExchangeById(int id) {
        try {
            exchangeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Deletion of an Exchange failed " + e.getMessage());
        }
    }

    /**
     * Updates an `Exchange` in the database and optionally marks the items
     * involved in the exchange as unavailable if the exchange is accepted.
     *
     * @param exchange The `Exchange` object with updated information.
     * @param accepted A boolean indicating if the exchange has been accepted.
     * @return The updated `Exchange` object.
     * @throws RuntimeException If the `Exchange` does not exist or an error occurs during the update.
     */
    public Exchange updateExchange(Exchange exchange, boolean accepted) {
        try {
            if (!exchangeRepository.existsById(exchange.getId_exchange())) {
                throw new RuntimeException("Exchange not found with ID: " + exchange.getId_exchange());
            }
            if(accepted) {
                int requestedObjectId = exchange.getRequestedObjectId();
                int offeredObjectId = exchange.getOfferedObjectId();
                itemService.getItemById(requestedObjectId).setAvailable(false);
                itemService.getItemById(offeredObjectId).setAvailable(false);
            }
            return exchangeRepository.save(exchange);
        } catch (Exception e) {
            throw new RuntimeException("Updating of an Exchange failed " + e.getMessage());
        }
    }

}
