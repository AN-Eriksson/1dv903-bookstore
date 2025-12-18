package me.andreaseriksson.bookstore.controller;

import jakarta.servlet.http.HttpSession;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.service.CheckoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    public String checkout(
            HttpSession session,
            Model model
    ) {

        Member member = (Member) session.getAttribute("member");

        int ono = checkoutService.placeOrder(member);
        return "invoice";
    }

}
