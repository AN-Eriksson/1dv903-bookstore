package me.andreaseriksson.bookstore.controller;

import me.andreaseriksson.bookstore.repository.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CheckoutController {
    private final CartRepository cartRepository;

    public CheckoutController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }


    @GetMapping("/checkout")
    public String checkout() {
        return "invoice";
    }

}
