package helha.trocappbackend.controllers;


import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.services.EmailService;
import helha.trocappbackend.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private EmailService emailService;

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
