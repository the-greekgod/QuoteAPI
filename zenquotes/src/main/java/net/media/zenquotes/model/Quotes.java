package net.media.zenquotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quotes")
public class Quotes {

    @Id
    @JsonIgnore
    private String id;

    @NotBlank(message="Quote is required")
    private String quote;

    @NotBlank(message="Author is required")
    private String author;

    @NotBlank(message="Language is required")
    @JsonIgnore
    private String language;

    @JsonIgnore
    private String[] category ;

}
