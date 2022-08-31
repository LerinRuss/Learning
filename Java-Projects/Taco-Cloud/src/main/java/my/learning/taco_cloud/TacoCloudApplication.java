package my.learning.taco_cloud;

import my.learning.taco_cloud.data.IngredientRepository;
import my.learning.taco_cloud.security.JpaUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TacoCloudApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login");
	}

	@Bean
//	@Profile("dev")
//	@Profile({"dev", "qa"})
	@Profile("!prod")
	public CommandLineRunner dataLoader(IngredientRepository ingredientRepository,
										JpaUserRepository userRepository,
										PasswordEncoder passwordEncoder) {

	}
}
