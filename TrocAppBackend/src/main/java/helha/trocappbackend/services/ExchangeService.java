package helha.trocappbackend.services;

import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.models.Item;
import helha.trocappbackend.repositories.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExchangeService {
    @Autowired
    private ExchangeRepository exchangeRepository;
    @Autowired
    private ItemService itemService;

    public Exchange addExchange(Exchange exchange) {
        try {
            int requestedObjectId = exchange.getRequestedObjectId();
            int offeredObjectId = exchange.getOfferedObjectId();
            itemService.getItemById(requestedObjectId).setAvailable(false);
            itemService.getItemById(offeredObjectId).setAvailable(false);
            return exchangeRepository.save(exchange);
        } catch (Exception e) {
            throw new RuntimeException("Adding an Exchange failed " + e.getMessage());
        }
    }

    public List<Exchange> getAllExchanges() {
        try {
            return exchangeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get of all Exchanges failed " + e.getMessage());
        }
    }

    public Exchange getExchangeById(int id) {
        try {
            Optional<Exchange> exchange = exchangeRepository.findById(id);
            return exchange.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Get of an Exchange failed " + e.getMessage());
        }
    }

    public List<Exchange> getAllExchangesByUserID(int userID) {
        try {
            return exchangeRepository.findAll().stream().filter(exchange -> exchange.getInitiator().getId()==userID | exchange.getReceiver().getId()==userID).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Get of all My Exchanges failed " + e.getMessage());
        }
    }

    public void deleteExchangeById(int id) {
        try {
            exchangeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Deletion of an Exchange failed " + e.getMessage());
        }
    }

    public Exchange updateExchange(Exchange exchange) {
        try {
            if (!exchangeRepository.existsById(exchange.getId_exchange())) {
                throw new RuntimeException("Exchange not found with ID: " + exchange.getId_exchange());
            }
            return exchangeRepository.save(exchange);
        } catch (Exception e) {
            throw new RuntimeException("Updating of an Exchange failed " + e.getMessage());
        }
    }

}
