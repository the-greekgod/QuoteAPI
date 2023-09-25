package net.media.zenquotes.cache;

import lombok.extern.slf4j.Slf4j;
import net.media.zenquotes.customException.DataNotFoundException;
import net.media.zenquotes.model.Quotes;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Slf4j
public class UserCacheService {
    @Cacheable(value="usersCache", key = "#user")
    public Quotes getRandomQuoteForUser(List<Quotes> quoteData, String user) throws DataNotFoundException {
        log.info("Caching data for key : "+user);
        if (quoteData.isEmpty()) {
            throw new DataNotFoundException("No matching quotes found.");
        }
        return quoteData.get(new Random().nextInt(quoteData.size())) ;
    }
}
