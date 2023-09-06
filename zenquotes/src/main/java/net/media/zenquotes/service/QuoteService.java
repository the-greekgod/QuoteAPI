package net.media.zenquotes.service;

import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.model.Quotes;
import net.media.zenquotes.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {
    @Autowired
    private QuoteRepository quoteRepo ;

    public ResponseEntity<?> getCustomizedQuote(String browser, String os, String country, String user) throws DataNotFoundException {

        List<Quotes> customizedQuote = null;

        if(country!=null && browser!=null && country.equalsIgnoreCase("US") && browser.equalsIgnoreCase("chrome")){
            customizedQuote = quoteRepo.findAllByAuthor("Unknown");
        }
        else if(country!=null && country.equalsIgnoreCase("US")) {
            customizedQuote = quoteRepo.findAllByLanguage("English");
        }
        else if(browser!=null && browser.equalsIgnoreCase("chrome")){
            customizedQuote = quoteRepo.findByAuthorStartsWith("William Shakespeare");
        }
        else if(os !=null && os.equalsIgnoreCase("windows")) {
            customizedQuote = quoteRepo.findAllByLanguage("Marathi");
        }
        else if(user !=null) {
            switch (user) {
                case "1":
                    customizedQuote = quoteRepo.findAllByLanguage("English");
                    break;
                case "2":
                    customizedQuote = quoteRepo.findAllByLanguage("Marathi");
                    break;
                case "3":
                    customizedQuote = quoteRepo.findAllByLanguage("Hindi");
                    break;
                default:
                    throw  new DataNotFoundException() ;
            }
        }
        else{
            customizedQuote = quoteRepo.findAll() ;
        }
        return new ResponseEntity<>(getRandomQuote(customizedQuote), HttpStatus.OK) ;

    }

    public Quotes getRandomQuote(List<Quotes> quoteData) throws DataNotFoundException {
        if (quoteData.isEmpty()) {
            throw new DataNotFoundException("No matching quotes found.");
        }
        return quoteData.get(new Random().nextInt(quoteData.size())) ;
    }
}
