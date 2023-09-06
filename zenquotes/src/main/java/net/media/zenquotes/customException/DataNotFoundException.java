package net.media.zenquotes.customException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataNotFoundException extends Throwable {

    private String message ;
    public DataNotFoundException(String s) {
        this.message = s ;
    }
}
