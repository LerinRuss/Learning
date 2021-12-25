package my.learning.spring.social.config;

import org.springframework.social.UserIdSource;

public class TestUserIdSource implements UserIdSource {
    private int id;

    @Override
    public String getUserId() {
        return "test " + id;
    }
}
