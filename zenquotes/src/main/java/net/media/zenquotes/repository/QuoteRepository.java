package net.media.zenquotes.repository;
import net.media.zenquotes.model.Quotes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends MongoRepository<Quotes, String> {

}
