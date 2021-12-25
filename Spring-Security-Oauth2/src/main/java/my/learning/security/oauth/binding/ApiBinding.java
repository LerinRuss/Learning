package my.learning.security.oauth.binding;

import java.io.IOException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

public abstract class ApiBinding {
    protected RestTemplate restTemplate;

    public ApiBinding(String accessToken) {
        this.restTemplate = new RestTemplate();

        if (accessToken == null) {
            throw new IllegalStateException("Can't access the API without an access token");
        }

        this.restTemplate.getInterceptors().add(getBearerTokenInterceptor(accessToken));
    }

    private ClientHttpRequestInterceptor getBearerTokenInterceptor(String accessToken) {
        return (request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + accessToken);

            return execution.execute(request, body);
        };
    }
}
