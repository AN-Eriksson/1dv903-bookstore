package me.andreaseriksson.bookstore.controller;

import jakarta.servlet.http.HttpSession;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.model.OrderDto;
import me.andreaseriksson.bookstore.service.CheckoutService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkout")
    public String checkout(
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Member member = (Member) session.getAttribute("member");
        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "Please log in before checking out.");
            return "redirect:/login";
        }

        try {
            OrderDto order = checkoutService.placeOrder(member);
            model.addAttribute("order", order);
            return "invoice";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable to place order right now. Please try again later.");
            return "redirect:/cart";
        }
    }

}
