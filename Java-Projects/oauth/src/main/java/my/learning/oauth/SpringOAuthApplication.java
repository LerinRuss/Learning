package my.learning.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@SpringBootApplication
public class SpringOAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOAuthApplication.class, args);
	}

}
