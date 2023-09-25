package net.media.zenquotes.controller;

import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.model.QueryParams;
import net.media.zenquotes.model.Quotes;
import net.media.zenquotes.service.QuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/quotes")
public class QuoteController {
    private final QuoteService quoteService ;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public ResponseEntity<Quotes> getCustomizedQuote(@ModelAttribute QueryParams queries) throws DataNotFoundException {
        Quotes customizedQuote = quoteService.getCustomizedQuote(queries) ;
        return new ResponseEntity<>(customizedQuote, HttpStatus.OK) ;
    }


}
