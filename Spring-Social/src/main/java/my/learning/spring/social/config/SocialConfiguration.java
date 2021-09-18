package my.learning.spring.social.config;

import my.learning.spring.social.adapter.SimpleSignInAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.connect.web.ProviderSignInInterceptor;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {
    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
        connectionFactoryConfigurer.addConnectionFactory(new FacebookConnectionFactory(
                environment.getProperty("spring.social.facebook.appId"),
                environment.getProperty("spring.social.facebook.appSecret")));
        connectionFactoryConfigurer.addConnectionFactory(new GoogleConnectionFactory(
                environment.getProperty("spring.social.google.appId"),
                environment.getProperty("spring.social.google.appSecret")));
    }

    @Override
    public UserIdSource getUserIdSource() {
        return new TestUserIdSource();
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
    }

    @Bean
    public ConnectController connectController(ConnectionFactoryLocator locator,
                                               ConnectionRepository repository) {
        return new ConnectController(locator, repository);
    }

    @Bean
    public SignInAdapter signInAdapter() {
        return new SimpleSignInAdapter();
    }

    @Bean
    public ProviderSignInController providerSignInController(ConnectionFactoryLocator locator,
                                                             UsersConnectionRepository usersConnectionRepository,
                                                             SignInAdapter signInAdapter) {
        var provider = new ProviderSignInController(locator, usersConnectionRepository, signInAdapter);
        provider.addSignInInterceptor(new GoogleProviderSignInInterceptor());

        return provider;
    }

    private class GoogleProviderSignInInterceptor implements ProviderSignInInterceptor<Google> {

        @Override
        public void preSignIn(ConnectionFactory<Google> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
            parameters.add("scope", "profile");
        }

        @Override
        public void postSignIn(Connection<Google> connection, WebRequest request) {

        }
    }
}
