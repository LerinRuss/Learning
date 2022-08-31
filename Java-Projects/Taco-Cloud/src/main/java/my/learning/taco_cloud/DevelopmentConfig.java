package my.learning.taco_cloud;

import my.learning.taco_cloud.data.IngredientRepository;
import my.learning.taco_cloud.security.JpaUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile({"!prod", "!qa"})
@Configuration
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingredientRepository,
                                        JpaUserRepository userRepository,
                                        PasswordEncoder passwordEncoder) {

    }
}
