package io.thundra.foresight.quotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuotesService {
    private final QuotesRepository quotesRepository;

    @Autowired
    public QuotesService(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    public Optional<Quote> get(String id) {
        return quotesRepository.findById(id);
    }

    public void save(Quote quote) {
        quotesRepository.save(quote);
    }
}
