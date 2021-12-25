package my.learning.spring.social.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {
    @Bean
    public Map<String, List<String>> myBean() {
        return Collections.emptyMap();
    }
}
