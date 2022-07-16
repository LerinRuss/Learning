package my.learning.taco_cloud.web;

import java.security.Principal;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import my.learning.taco_cloud.Order;
import my.learning.taco_cloud.User;
import my.learning.taco_cloud.data.JpaOrderRepository;
import my.learning.taco_cloud.security.JpaUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

@RequiredArgsConstructor
@Log4j2
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final JpaOrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm(Model model) {
        model.addAttribute("order", new Order());

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid @ModelAttribute("order") Order order, @AuthenticationPrincipal User user,
                               Errors errors, SessionStatus sessionStatus) {
        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        orderRepository.save(order);
        sessionStatus.setComplete();

        log.info("Order submitted: {}", order);

        return "redirect:/";
    }

    @GetMapping
    public String orderForUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("order", orderRepository.findByUserOrderByPlacedAtDesc(user));

        return "orderList";
    }
}
