package my.learning.spring.social.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class HelloController {
//    private final Facebook facebook;
    @Value("${spring.social.facebook.appId}")
    private String appId;
    @Value("${spring.social.facebook.appSecret}")
    private String appSecret;

    private final ConnectionFactoryLocator factoryLocator;

    @GetMapping
    public String helloFacebook(Model model, ConnectionRepository connectionRepository) {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        Facebook facebook = connection.getApi();

        if (!facebook.isAuthorized()) {
            return "redirect:/connect/facebook";
        }

        model.addAttribute(facebook.userOperations().getUserProfile());
        PagedList<Post> homeFeed = facebook.feedOperations().getHomeFeed();
        model.addAttribute("feed", homeFeed);

        return "home";
    }

    @GetMapping("/signIn")
    public String signIn() {
        return "signIn";
    }

    @PostMapping("/signIn/code/{authCode}")
    public void signCodeIn(@PathVariable String authCode, HttpServletResponse response) throws IOException {
        var factory = new FacebookConnectionFactory(appId, appSecret);
        OAuth2Operations operations = factory.getOAuthOperations();
        var parameters = new OAuth2Parameters();
        parameters.setRedirectUri("https://localhost:8080");

        String authorizeUrl = operations.buildAuthorizeUrl(parameters);
        response.sendRedirect(authorizeUrl);

        AccessGrant accessGrant;
        try {
             accessGrant = operations
                    .exchangeForAccess(authCode, "https://localhost:8080", null);
        } catch (RuntimeException ex) {
            throw ex;
        }

        Connection<Facebook> connection = factory.createConnection(accessGrant);
    }

    @PostMapping("/signIn/token/{accessToken}")
    public void signTokenIn(@PathVariable String accessToken, HttpServletResponse response) throws IOException {
        var connectionFactory = new FacebookConnectionFactory(appId, appSecret);
        OAuth2Operations operations = connectionFactory.getOAuthOperations();
        var parameters = new OAuth2Parameters();
        parameters.setRedirectUri("https://my-callback-url");

        String authorizeUrl = operations.buildAuthorizeUrl(GrantType.IMPLICIT_GRANT, parameters);
        response.sendRedirect(authorizeUrl);

        // upon receiving the callback from the provider:
        var accessGrant = new AccessGrant(accessToken);
        Connection<Facebook> connection = connectionFactory.createConnection(accessGrant);
    }
}
