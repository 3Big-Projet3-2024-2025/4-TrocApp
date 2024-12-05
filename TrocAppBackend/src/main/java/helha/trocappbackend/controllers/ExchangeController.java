package helha.trocappbackend.controllers;


import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.services.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @PostMapping
    public Exchange addExchange(@RequestBody Exchange exchange) {
        return exchangeService.addExchange(exchange);
    }

    @GetMapping
    public List<Exchange> getAllExchanges() {
        return exchangeService.getAllExchanges();
    }

    @GetMapping("/{id}")
    public Exchange getExchangeById(@PathVariable int id) {
        return exchangeService.getExchangeById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteExchangeById(@PathVariable int id) {
        exchangeService.deleteExchangeById(id);
    }

    @PutMapping
    public Exchange updateExchange(@RequestBody Exchange exchange) {
        return exchangeService.updateExchange(exchange);
    }

}
