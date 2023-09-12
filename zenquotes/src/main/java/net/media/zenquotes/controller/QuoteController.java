package net.media.zenquotes.controller;

import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.model.Quotes;
import net.media.zenquotes.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/quotes")
public class QuoteController {

    @Autowired
    private QuoteService quoteService ;


    @GetMapping("")
    public ResponseEntity<?> getCustomizedQuote(
            @RequestParam(required = false) String browser,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String os,
            @RequestParam(required = false) String user
    ) throws DataNotFoundException {

        List<Quotes> quotes = quoteService.getCustomizedQuote(browser, country, os, user) ;
        Quotes customizedQuote ;
        if(user!=null){
            customizedQuote = quoteService.getRandomQuoteForUser(quotes, user) ;
        }
        else{
            customizedQuote = quoteService.getRandomQuote(quotes) ;
        }
//        Quotes customizedQuote = quoteService.getRandomQuote(quotes) ;

        return new ResponseEntity<>(customizedQuote, HttpStatus.OK) ;

    }


}
