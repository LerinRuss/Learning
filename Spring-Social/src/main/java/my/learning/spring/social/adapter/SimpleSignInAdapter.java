package my.learning.spring.social.adapter;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSignInAdapter implements SignInAdapter {
    @Override
    public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
        return null;
    }
}
