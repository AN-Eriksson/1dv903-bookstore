package me.andreaseriksson.bookstore.controller;

import me.andreaseriksson.bookstore.model.CartItem;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.repository.CartRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;

    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @GetMapping
    public String viewCart(@SessionAttribute(value = "member", required = false) Member member,
                           Model model) {
        List<CartItem> cartItems = cartRepository.getCartContents(member);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", calculateTotal(cartItems));
        return "cart";
    }

    private double calculateTotal(List<CartItem> items) {
        double total = 0;
        for (CartItem item : items) {
            total += item.getQty() * item.getBook().getPrice();
        }
        return total;
    }
}