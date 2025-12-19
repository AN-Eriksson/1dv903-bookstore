package me.andreaseriksson.bookstore.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.repository.MemberRepository;
import me.andreaseriksson.bookstore.service.MemberService;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("member", new Member());

        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute Member member,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) {

        String redirectionLink = validateRegisterFormInput(redirectAttributes, request);
        if (redirectionLink != null) return redirectionLink;

        try {
            memberService.save(member);
            redirectAttributes.addFlashAttribute("message", "Registration successful!");
            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed, please try again");
            return "redirect:/register";
        }
    }

    private static @Nullable String validateRegisterFormInput(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String fnameParam = request.getParameter("fname");
        String lnameParam = request.getParameter("lname");
        String emailParam = request.getParameter("email");
        String passwordParam = request.getParameter("password");
        String addressParam = request.getParameter("address");
        String cityParam = request.getParameter("city");
        String phoneParam = request.getParameter("phone");
        String zipParam = request.getParameter("zip");

        if (fnameParam == null || fnameParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "First name is required");
            return "redirect:/register";
        }
        if (lnameParam == null || lnameParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Last name is required");
            return "redirect:/register";
        }
        if (emailParam == null || emailParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email is required");
            return "redirect:/register";
        }

        if (!emailParam.matches("^[\\w.%+\\-]+@[\\w.\\-]+\\.[A-Za-z]{2,}$")) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format");
            return "redirect:/register";
        }
        if (passwordParam == null || passwordParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password is required");
            return "redirect:/register";
        }
        if (passwordParam.length() < 4) {
            redirectAttributes.addFlashAttribute("error", "Password must be at least 4 characters");
            return "redirect:/register";
        }
        if (addressParam == null || addressParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Address is required");
            return "redirect:/register";
        }
        if (cityParam == null || cityParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "City is required");
            return "redirect:/register";
        }
        if (phoneParam != null && !phoneParam.trim().isEmpty()) {
            if (!phoneParam.matches("^[0-9\\s+\\-()]+$")) {
                redirectAttributes.addFlashAttribute("error", "Invalid phone number");
                return "redirect:/register";
            }
        }

        if (zipParam == null || zipParam.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Zip is required");
            return "redirect:/register";
        }

        int zip;
        try {
            zip = Integer.parseInt(zipParam.trim());
            if (zip <= 0) {
                redirectAttributes.addFlashAttribute("error", "Zip must be a positive number");
                return "redirect:/register";
            }
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Zip must be a number");
            return "redirect:/register";
        }
        return null;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("login", new Member());

        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("login") Member login,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        String redirectLink = validateLoginForm(login, redirectAttributes);
        if (redirectLink != null) return redirectLink;

        try {
            Optional<Member> found = memberService.findByEmail(login.getEmail().trim());

            if (found.isPresent()) {
                Member member = found.get();
                if (member.getPassword() != null && member.getPassword().equals(login.getPassword())) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("member", member);
                    session.setAttribute("memberName", member.getFname() + " " + member.getLname());
                    return "redirect:/";
                }
            }

            redirectAttributes.addFlashAttribute("error", "Invalid login - please try again!");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login failed, please try again");
            return "redirect:/login";
        }
    }

    private static @Nullable String validateLoginForm(Member login, RedirectAttributes redirectAttributes) {
        if (login.getEmail() == null || login.getEmail().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email is required");
            return "redirect:/login";
        }
        if (login.getPassword() == null || login.getPassword().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password is required");
            return "redirect:/login";
        }
        return null;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request,
                         RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        redirectAttributes.addFlashAttribute("message", "Logout successful - please visit again!");
        return "redirect:/";
    }

}
