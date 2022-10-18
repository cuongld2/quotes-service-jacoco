package io.thundra.foresight.quotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("quotes")
public class QuotesController {
    private final QuotesService quotesService;

    @Autowired
    public QuotesController(
        QuotesService quotesService
    ) {
        this.quotesService = quotesService;
    }

    @GetMapping("{id}")
    public Quote get(
        @PathVariable String id
    ) {
        var quote = quotesService.get(id);

        if (quote.isPresent()) {
            return quote.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
        @RequestBody Quote quote
    ) {
        quotesService.save(quote);
    }
}
