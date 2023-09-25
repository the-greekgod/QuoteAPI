package net.media.zenquotes.service;

import lombok.extern.slf4j.Slf4j;
import net.media.zenquotes.cache.UserCacheService;
import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.model.QueryFilter;
import net.media.zenquotes.model.QueryParams;
import net.media.zenquotes.model.Quotes;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.cache.Cache.ValueWrapper;

import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class QuoteService {
    private final MongoTemplate mongoTemplate ;
    private final KieContainer kieContainer;
    private final UserCacheService cacheService;
    private final CacheManager cacheManager;
    public QuoteService(MongoTemplate mongoTemplate, KieContainer kieContainer, UserCacheService cacheService, CacheManager cacheManager) {
        this.mongoTemplate = mongoTemplate;
        this.kieContainer = kieContainer;
        this.cacheService = cacheService;
        this.cacheManager = cacheManager;
    }


    public Quotes getCustomizedQuote(QueryParams queryParams) throws DataNotFoundException {
        QueryFilter filterObj = getQueryFilter(queryParams);
        Query query = getQuery(filterObj);
//        System.out.println("After: " + query);
        return getRandomQuote(queryParams, query) ;
    }

    private Quotes getRandomQuote(QueryParams queryParams, Query query) throws DataNotFoundException {
        String cacheKey = queryParams.getKey();
        ValueWrapper data = getDataFromCache(cacheKey);
        if(data!=null){
            log.info("Getting data from cache for key : "+ cacheKey);
            return (Quotes) data.get();
        }
        log.info("Getting quotes from DB");
        List<Quotes> quotes =  mongoTemplate.find(query, Quotes.class);
        if (quotes.isEmpty()) {
            throw new DataNotFoundException("No matching quotes found.");
        }
        if(queryParams.getUser()!=null){
            return cacheService.getRandomQuoteForUser(quotes, cacheKey) ;
        }
        return quotes.get(new Random().nextInt(quotes.size())) ;
    }

    private ValueWrapper getDataFromCache(String cacheKey) {
        return Optional.ofNullable(cacheManager.getCache("usersCache")).map(cache -> cache.get(cacheKey)).orElse(null);
    }


    private QueryFilter getQueryFilter(QueryParams queryObj) {
        QueryFilter obj = new QueryFilter();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(queryObj);
        kieSession.insert(obj);
        kieSession.fireAllRules();
        kieSession.dispose();
        return obj;
    }

    private Query getQuery(QueryFilter filterObj) {
        Query query = new Query();
//        System.out.println("Before: " + query);
        if (filterObj.getFilterByLanguage()!=null) {
            query.addCriteria(Criteria.where("language").is(filterObj.getFilterByLanguage()));
        }
        if (filterObj.getFilterByAuthor()!=null) {
            query.addCriteria(Criteria.where("author").is(filterObj.getFilterByAuthor()));
        }
        if (filterObj.getFilterByCategory()!=null) {
            query.addCriteria(Criteria.where("category").is(filterObj.getFilterByCategory()));
        }
        return query;
    }


}
