package my.learning.spring;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMsg msg) {
        return new Greeting(String.format("Hello, %s!", HtmlUtils.htmlEscape(msg.getName())));
    }
}
