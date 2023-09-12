package net.media.zenquotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ZenquotesApplication {

	public static void main(String[] args) {

		SpringApplication.run(ZenquotesApplication.class, args);

	}

}
