package helha.trocappbackend.services;

import helha.trocappbackend.models.Exchange;
import helha.trocappbackend.repositories.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeService {
    @Autowired
    private ExchangeRepository exchangeRepository;

    public Exchange addExchange(Exchange exchange) {
        try {
            return exchangeRepository.save(exchange);
        } catch (Exception e) {
            throw new RuntimeException("Ajout de Exchange a echoué " + e.getMessage());
        }
    }

    public List<Exchange> getAllExchanges() {
        try {
            return exchangeRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get des Exchanges a echoué " + e.getMessage());
        }
    }

    public Exchange getExchangeById(int id) {
        try {
            Optional<Exchange> exchange = exchangeRepository.findById(id);
            return exchange.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Get de Exchange a echoué " + e.getMessage());
        }
    }

    public void deleteExchangeById(int id) {
        try {
            exchangeRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Suppression de Exchange a echoué " + e.getMessage());
        }
    }

    public Exchange updateExchange(Exchange exchange) {
        try {
            return exchangeRepository.save(exchange);
        } catch (Exception e) {
            throw new RuntimeException("Mise a jour de Exchange a echoué " + e.getMessage());
        }
    }

}
