package my.learning.taco_cloud;

import static my.learning.taco_cloud.entity.Ingredient.*;

import java.util.Arrays;
import my.learning.taco_cloud.data.IngredientRepository;
import my.learning.taco_cloud.data.TacoRepository;
import my.learning.taco_cloud.entity.Ingredient;
import my.learning.taco_cloud.entity.Taco;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SpringBootApplication
public class TacoCloudApplication {
    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    @EnableWebSecurity
    public static class WebSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            return httpSecurity.csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                    .build();
        }
    }

    @Profile("!prod")
    @Configuration
    public static class DevConfig {
        @Bean
        public CommandLineRunner dataLoader(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    Ingredient flourTortilla = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
                    Ingredient cornTortilla = new Ingredient("COTO", "Corn Tortilla", Type.WRAP);
                    Ingredient groundBeef = new Ingredient("GRBF", "Ground Beef", Type.PROTEIN);
                    Ingredient carnitas = new Ingredient("CARN", "Carnitas", Type.PROTEIN);
                    Ingredient tomatoes = new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES);
                    Ingredient lettuce = new Ingredient("LETC", "Lettuce", Type.VEGGIES);
                    Ingredient cheddar = new Ingredient("CHED", "Cheddar", Type.CHEESE);
                    Ingredient jack = new Ingredient("JACK", "Monterrey Jack", Type.CHEESE);
                    Ingredient salsa = new Ingredient("SLSA", "Salsa", Type.SAUCE);
                    Ingredient sourCream = new Ingredient("SRCR", "Sour Cream", Type.SAUCE);
                    ingredientRepository.save(flourTortilla);
                    ingredientRepository.save(cornTortilla);
                    ingredientRepository.save(groundBeef);
                    ingredientRepository.save(carnitas);
                    ingredientRepository.save(tomatoes);
                    ingredientRepository.save(lettuce);
                    ingredientRepository.save(cheddar);
                    ingredientRepository.save(jack);
                    ingredientRepository.save(salsa);
                    ingredientRepository.save(sourCream);

                    Taco taco1 = new Taco();
                    taco1.setName("Carnivore");
                    taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
                    tacoRepository.save(taco1);

                    Taco taco2 = new Taco();
                    taco2.setName("Bovine Bounty");
                    taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
                    tacoRepository.save(taco2);

                    Taco taco3 = new Taco();
                    taco3.setName("Veg-Out");
                    taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
                    tacoRepository.save(taco3);
                }
            };
        }
    }
}
