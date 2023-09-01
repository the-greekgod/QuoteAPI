package net.media.zenquotes.controller;

import net.media.zenquotes.model.Quotes;
import net.media.zenquotes.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class QuoteController {
    @Autowired
    private QuoteRepository quoteRepo ;

    @GetMapping("/quotes")
    public ResponseEntity<?> getAllQuotes(){
        List<Quotes> quotes = quoteRepo.findAll() ;
        if(quotes.size()>0){
            return new ResponseEntity<>(quotes, HttpStatus.OK) ;
        }
        else{
            return new ResponseEntity<>("Quotes not found", HttpStatus.NOT_FOUND) ;
        }
    }

    @PostMapping("/quotes")
    public ResponseEntity<?> createQuote(@RequestBody Quotes quote){
        try {
            quote.setInsertedAt(new Date(System.currentTimeMillis()));
            quoteRepo.save(quote);
            return new ResponseEntity<>(quote, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR) ;
        }
    }

    @GetMapping("/quotes/{id}")
    public ResponseEntity<?> getQuoteByID(@PathVariable("id") String id){
        Optional<Quotes> quoteByID = quoteRepo.findById(id);
        if(quoteByID.isPresent()){
            return new ResponseEntity<>(quoteByID.get() , HttpStatus.OK) ;
        }
        else{
            return new ResponseEntity<>("No quote by id : " + id , HttpStatus.NOT_FOUND) ;
        }
    }



}
