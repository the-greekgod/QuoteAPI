package net.media.zenquotes.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryFilter {

    private String filterByLanguage ;
    private String filterByAuthor ;
    private String filterByCategory  ;

}
