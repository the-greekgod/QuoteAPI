package net.media.zenquotes.service;

import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.model.QueryParams;
import net.media.zenquotes.model.Quotes;
//import net.media.zenquotes.repository.QuoteRepository;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {
//    @Autowired
//    private QuoteRepository quoteRepo ;
    @Autowired
    private CacheManager cacheManager ;
    @Autowired
    private MongoTemplate mongoTemplate ;
    private final KieContainer kieContainer;
    public QuoteService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public QueryParams applyRules(QueryParams queryObj) {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(queryObj);
        kieSession.fireAllRules();
        kieSession.dispose();
        return queryObj;
    }


    public List<Quotes> getCustomizedQuote(String browser, String country, String os, String user) throws DataNotFoundException {

        QueryParams queryParams = new QueryParams();

//        if(user!=null){
//            queryParams.setUser(user);
//            getQuoteByUser(user) ;
//        }


        queryParams.setBrowser(browser);
        queryParams.setCountry(country);
        queryParams.setOs(os);
        queryParams.setUser(user);

        QueryParams obj = applyRules(queryParams) ;

        List<Quotes> customizedQuote ;

//        if (obj.getFilterByLanguage() != null && !obj.getFilterByLanguage().isEmpty()) {
//            customizedQuote = quoteRepo.findAllByLanguage(obj.getFilterByLanguage()) ;
//        }
//        else if (obj.getFilterByAuthor() != null && !obj.getFilterByAuthor().isEmpty()) {
//            customizedQuote = quoteRepo.findAllByAuthor(obj.getFilterByAuthor()) ;
//        }
//        else if (obj.getFilterByCategory() != null && !obj.getFilterByCategory().isEmpty()) {
//            customizedQuote = quoteRepo.findAllByCategoryContains(obj.getFilterByCategory()) ;
//        }
//        else{
//            customizedQuote = quoteRepo.findAllByLanguage("English") ;
//        }
//        return customizedQuote ;

        Query query = new Query();
        if (obj.getFilterByLanguage()!=null) {
            query.addCriteria(Criteria.where("language").is(obj.getFilterByLanguage()));
        }
        if (obj.getFilterByAuthor()!=null) {
            query.addCriteria(Criteria.where("author").is(obj.getFilterByAuthor()));
        }
        if (obj.getFilterByCategory()!=null) {
            query.addCriteria(Criteria.where("category").is(obj.getFilterByCategory()));
        }

        customizedQuote = mongoTemplate.find(query, Quotes.class);
        return customizedQuote;


    }

    public Quotes getRandomQuote(List<Quotes> quoteData) throws DataNotFoundException {
        if (quoteData.isEmpty()) {
            throw new DataNotFoundException("No matching quotes found.");
        }
        return quoteData.get(new Random().nextInt(quoteData.size())) ;
    }


    @Cacheable(value="usersCache", key = "#user")
    public Quotes getRandomQuoteForUser(List<Quotes> quoteData, String user) throws DataNotFoundException {
        if (quoteData.isEmpty()) {
            throw new DataNotFoundException("No matching quotes found.");
        }
        return quoteData.get(new Random().nextInt(quoteData.size())) ;
    }





}
