package me.andreaseriksson.bookstore.controller;

import me.andreaseriksson.bookstore.model.CartItem;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String viewCart(@SessionAttribute(value = "member", required = false) Member member,
                           Model model) {
        List<CartItem> cartItems = cartService.getCartContents(member);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartService.calculateTotal(cartItems));
        return "cart";
    }
}