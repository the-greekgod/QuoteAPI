package net.media.zenquotes.controller;

import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService ;

    @GetMapping("")
    public ResponseEntity<?> getCustomizedQuote(
            @RequestParam(required = false) String browser,
            @RequestParam(required = false) String os,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String user
    ) throws DataNotFoundException {
        return quoteService.getCustomizedQuote(browser, os, country, user) ;
    }

}
