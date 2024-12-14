package helha.trocappbackend.controllers;


import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<Exchange> addExchange(@RequestBody Exchange exchange) {
        try {
            Exchange exchange1 = exchangeService.addExchange(exchange);
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

    @GetMapping("/{id}")
    public ResponseEntity<Exchange> getExchangeById(@PathVariable int id) {
        try {
            Exchange exchange = exchangeService.getExchangeById(id);
            if (exchange != null) {
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

    @PutMapping
    public ResponseEntity<Exchange> updateExchange(@RequestBody Exchange exchange) {
        try {
            Exchange exchange1 = exchangeService.updateExchange(exchange);
            return ResponseEntity.ok(exchange1);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

}
