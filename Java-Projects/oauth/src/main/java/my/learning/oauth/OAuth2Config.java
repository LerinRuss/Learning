package my.learning.oauth;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OAuth2Config {
    @EnableWebSecurity
    public static class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests((requests) -> requests.anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                    .tokenEndpoint(tokenEndpointConfig -> tokenEndpointConfig
                        .accessTokenResponseClient(this.accessTokenResponseClient()))
                    .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(this.userService()))
                );
        }

        private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
            var accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();

            var oauth2TknResHttpMsgConverter = new OAuth2AccessTokenResponseHttpMessageConverter();
            var defaultOAuth2AccessTKNResConverter = new DefaultMapOAuth2AccessTokenResponseConverter();
            oauth2TknResHttpMsgConverter.setAccessTokenResponseConverter(
                source -> {
                    source.put(OAuth2ParameterNames.TOKEN_TYPE, OAuth2AccessToken.TokenType.BEARER.getValue());
                    return defaultOAuth2AccessTKNResConverter.convert(source);
                }
            );

            RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(),
                oauth2TknResHttpMsgConverter));

            restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

            accessTokenResponseClient.setRestOperations(restTemplate);

            return accessTokenResponseClient;
        }

        private OAuth2UserService<OAuth2UserRequest, OAuth2User> userService() {
            var userService = new DefaultOAuth2UserService();

            RestTemplate restTemplate = new RestTemplate();
            List<HttpMessageConverter<?>> modifiedConvertersList = restTemplate
                .getMessageConverters().stream()
                .filter(converter -> converter.getClass() != MappingJackson2HttpMessageConverter.class)
                .collect(Collectors.toList());
            modifiedConvertersList.add(new MappingJackson2HttpMessageConverter() {
                private final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_STRING_TO_OBJECT_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
                };

                @Override
                public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
                    Object res = super.read(type, contextClass, inputMessage);

                    if (PARAMETERIZED_RESPONSE_STRING_TO_OBJECT_TYPE.getType().equals(type)) {
                        Map<String, Object> rootMapCasted = (Map<String, Object>) res;

                        if (rootMapCasted.containsKey("response")
                            && rootMapCasted.get("response") instanceof ArrayList<?>) {
                            ArrayList<?> responseFieldListCasted = (ArrayList<?>) rootMapCasted.get("response");

                            if (responseFieldListCasted.size() == 1) {
                                return responseFieldListCasted.get(0);
                            }
                        }
                    }
                    return res;
                }
            });

            restTemplate.setMessageConverters(modifiedConvertersList);
            restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

            userService.setRestOperations(restTemplate);

            return userService;
        }

    }

//    @Bean
//    private OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
//        return new CustomOAuth2UserService();
//    }
//
//    private static class CustomOAuth2UserService extends DefaultOAuth2UserService {
//        private static final String MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute";
//        private static final String MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri";
//        private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE = new ParameterizedTypeReference<Map<String, Object>>() {
//        };
//
//        private Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();
//
//        private CustomOAuth2UserService() {
//            super();
//
//            super.setRequestEntityConverter(requestEntityConverter);
//        }
//
//        @Override
//        public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//
//            OAuth2User oAuth2User;
//            if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("vk")) {
//                oAuth2User = loadVkUser(oAuth2UserRequest);
//            } else {
//                oAuth2User = super.loadUser(oAuth2UserRequest);
//            }
//
//            return oAuth2User;
//        }
//
//
//        private OAuth2User loadVkUser(OAuth2UserRequest userRequest) {
//            Assert.notNull(userRequest, "userRequest cannot be null");
//            if (!StringUtils
//                .hasText(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri())) {
//                OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_INFO_URI_ERROR_CODE,
//                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: "
//                        + userRequest.getClientRegistration().getRegistrationId(),
//                    null);
//                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
//            }
//            String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
//                .getUserNameAttributeName();
//            if (!StringUtils.hasText(userNameAttributeName)) {
//                OAuth2Error oauth2Error = new OAuth2Error(MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE,
//                    "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: "
//                        + userRequest.getClientRegistration().getRegistrationId(),
//                    null);
//                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
//            }
//            RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
//            ResponseEntity<Map<String, Object>> response = getResponse(userRequest, request);
//            Map<String, Object> userAttributes = response.getBody();
//            Set<GrantedAuthority> authorities = new LinkedHashSet<>();
//            authorities.add(new OAuth2UserAuthority(userAttributes));
//            OAuth2AccessToken token = userRequest.getAccessToken();
//            for (String authority : token.getScopes()) {
//                authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
//            }
//
//            return new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
//        }
//
//        private ResponseEntity<Map<String, Object>> getResponse(OAuth2UserRequest userRequest, RequestEntity<?> request) {
//            try {
//                return this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE);
//            }
//            catch (OAuth2AuthorizationException ex) {
//                OAuth2Error oauth2Error = ex.getError();
//                StringBuilder errorDetails = new StringBuilder();
//                errorDetails.append("Error details: [");
//                errorDetails.append("UserInfo Uri: ")
//                    .append(userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri());
//                errorDetails.append(", Error Code: ").append(oauth2Error.getErrorCode());
//                if (oauth2Error.getDescription() != null) {
//                    errorDetails.append(", Error Description: ").append(oauth2Error.getDescription());
//                }
//                errorDetails.append("]");
//                oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
//                    "An error occurred while attempting to retrieve the UserInfo Resource: " + errorDetails.toString(),
//                    null);
//                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
//            }
//            catch (UnknownContentTypeException ex) {
//                String errorMessage = "An error occurred while attempting to retrieve the UserInfo Resource from '"
//                    + userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri()
//                    + "': response contains invalid content type '" + ex.getContentType().toString() + "'. "
//                    + "The UserInfo Response should return a JSON object (content type 'application/json') "
//                    + "that contains a collection of name and value pairs of the claims about the authenticated End-User. "
//                    + "Please ensure the UserInfo Uri in UserInfoEndpoint for Client Registration '"
//                    + userRequest.getClientRegistration().getRegistrationId() + "' conforms to the UserInfo Endpoint, "
//                    + "as defined in OpenID Connect 1.0: 'https://openid.net/specs/openid-connect-core-1_0.html#UserInfo'";
//                OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE, errorMessage, null);
//                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
//            }
//            catch (RestClientException ex) {
//                OAuth2Error oauth2Error = new OAuth2Error(INVALID_USER_INFO_RESPONSE_ERROR_CODE,
//                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.getMessage(), null);
//                throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex);
//            }
//        }
//    }
}
