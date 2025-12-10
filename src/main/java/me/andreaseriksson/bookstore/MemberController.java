package me.andreaseriksson.bookstore;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private final MemberRepository memberRepository;

    public MemberController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());

        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Member member) {
        memberRepository.save(member);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Member());

        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("login") Member login,
                               HttpServletRequest request) {
        Member found = memberRepository.findByEmail(login.getEmail());

        if (found != null && found.getPassword() != null && found.getPassword().equals(login.getPassword())) {
            HttpSession session = request.getSession(true);
            session.setAttribute("memberName", found.getFname() + " " + found.getLname());
            return "redirect:/";

        } else {
            return "redirect:/login";
        }
    }

}
