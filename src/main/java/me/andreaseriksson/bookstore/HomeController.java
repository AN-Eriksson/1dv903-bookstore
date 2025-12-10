package me.andreaseriksson.bookstore;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object name = session.getAttribute("memberName");
        if (name != null) {
            model.addAttribute("memberName", name.toString());
        }

        return "home";
    }
}
