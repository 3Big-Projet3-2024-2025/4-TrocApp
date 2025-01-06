package helha.trocappbackend.controllers;


import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.services.EmailService;
import helha.trocappbackend.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing `Exchange` resources.
 *
 * <p>This controller handles HTTP requests for operations related to exchanges,
 * including creating, retrieving, updating, and deleting exchange records.
 * Email notifications are sent to the receiver when a new exchange is proposed.</p>
 *
 * <p>Endpoints are exposed at `/exchanges`.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Add a new exchange
 * POST /exchanges
 *
 * // Get all exchanges
 * GET /exchanges
 * }</pre>
 *
 *  @author Hayriye Dogan
 *  @see helha.trocappbackend.controllers
 */
@RestController
@RequestMapping("/exchanges")
public class ExchangeController {
    /**
     * Service for managing `Exchange` entities.
     */
    @Autowired
    private ExchangeService exchangeService;
    /**
     * Service for sending email notifications.
     */
    @Autowired
    private EmailService emailService;

    /**
     * Adds a new exchange and sends an email notification to the receiver.
     *
     * @param exchange The `Exchange` object to be added.
     * @return A `ResponseEntity` containing the created `Exchange` or a `400 Bad Request` status if the operation fails.
     */
    @PostMapping
    public ResponseEntity<Exchange> addExchange(@RequestBody Exchange exchange) {
        try {
            Exchange exchange1 = exchangeService.addExchange(exchange);
            String nameReveiver = exchange1.getReceiver().getFirstName() + " " + exchange1.getReceiver().getLastName();
            String emailReceiver = exchange1.getReceiver().getEmail();
            String subjectMail = "Exchange Proposition";
            String bodyMail = "Hello, "+ nameReveiver +" you have a proposition of exchange for one of your item. Go to http://localhost:4200/exchanges to see its details";

            emailService.sendEmail(emailReceiver, subjectMail, bodyMail);
            return ResponseEntity.ok(exchange1);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves all exchanges from the database.
     *
     * @return A `ResponseEntity` containing the list of all exchanges or a `500 Internal Server Error` status if the operation fails.
     */
    @GetMapping
    public ResponseEntity<List<Exchange>> getAllExchanges() {
        try {
            List<Exchange> exchangeList = exchangeService.getAllExchanges();
            return ResponseEntity.ok(exchangeList);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Retrieves all exchanges associated with a specific user.
     *
     * @param id The ID of the user.
     * @return A `ResponseEntity` containing the list of exchanges or a `500 Internal Server Error` status if the operation fails.
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Exchange>> getExchangesByUserId(@PathVariable int id) {
        try {
            List<Exchange> exchangesOfUserId = exchangeService.getAllExchangesByUserID(id);
            return ResponseEntity.ok(exchangesOfUserId);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Retrieves an exchange by its ID.
     *
     * @param id The ID of the exchange.
     * @return A `ResponseEntity` containing the `Exchange` object or a `404 Not Found` status if the exchange is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Exchange> getExchangeById(@PathVariable int id) {
        try {
            Exchange exchange = exchangeService.getExchangeById(id);
            if (exchange == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(exchange);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Deletes an exchange by its ID.
     *
     * @param id The ID of the exchange to delete.
     * @return A `204 No Content` response if the deletion is successful or a `500 Internal Server Error` status if the operation fails.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchangeById(@PathVariable int id) {
        try {
            exchangeService.deleteExchangeById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }

    /**
     * Updates an existing exchange.
     *
     * <p>If the status is "Declined", the exchange will not affect item availability.
     * If the status is accepted, associated items will be marked as unavailable.</p>
     *
     * @param id       The ID of the exchange to update.
     * @param exchange The updated `Exchange` object.
     * @return A `ResponseEntity` containing the updated `Exchange` or a `400 Bad Request` status if the operation fails.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Exchange> updateExchange(@PathVariable int id,@RequestBody Exchange exchange) {
        try {
            Exchange exchange1;
            exchange.setId_exchange(id);
            if(Objects.equals(exchange.getStatus(), "Declined")) {
                exchange1 = exchangeService.updateExchange(exchange,false);
            } else {
                exchange1 = exchangeService.updateExchange(exchange, true);
            }
            return ResponseEntity.ok(exchange1);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

}
