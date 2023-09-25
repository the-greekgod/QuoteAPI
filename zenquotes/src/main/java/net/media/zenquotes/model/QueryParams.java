package net.media.zenquotes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class QueryParams {

    private String browser ;
    private String country ;
    private String os ;
    private String user ;

    public String getKey(){
        return Optional.ofNullable(browser).orElse("") + Optional.ofNullable(country).orElse("") +Optional.ofNullable(os).orElse("") +Optional.ofNullable(user).orElse("");
    }

}
