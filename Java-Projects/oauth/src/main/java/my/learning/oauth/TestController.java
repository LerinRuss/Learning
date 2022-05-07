package my.learning.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping
    public String test(OAuth2AuthenticationToken token) {
        System.out.println(token);
        OAuth2AuthorizedClient client = auth2AuthorizedClientService
            .loadAuthorizedClient(
                token.getAuthorizedClientRegistrationId(),
                token.getName());

        String accessToken = client.getAccessToken().getTokenValue();

        return accessToken;
    }
}
