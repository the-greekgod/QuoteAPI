package net.media.zenquotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quotes")
public class Quotes {
    @Id
    private String id;

    private String quote;

    private String author;

    @JsonIgnore
    private Date insertedAt ;

    @JsonIgnore
    private Date updatedAt ;

}
