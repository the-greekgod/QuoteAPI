package net.media.zenquotes.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryParams {
    private String browser ;
    private String country ;
    private String os ;
    private String user ;

    private String filterByLanguage ;
    private String filterByAuthor ;
    private String filterByCategory  ;

}
