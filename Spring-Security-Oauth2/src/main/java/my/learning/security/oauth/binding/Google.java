package my.learning.security.oauth.binding;

public class Google extends ApiBinding {
    private static final String USERINFO_URL = "https://www.googleapis.com/oauth2/v2/userinfo";

    public Google(String accessToken) {
        super(accessToken);
    }

    public Profile
}
