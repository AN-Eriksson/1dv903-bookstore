package me.andreaseriksson.bookstore.controller;

import jakarta.servlet.http.HttpSession;
import me.andreaseriksson.bookstore.model.Book;
import me.andreaseriksson.bookstore.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    private final BookRepository bookRepository;
    private static final int PAGE_SIZE = 5;

    public HomeController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        Object name = session.getAttribute("memberName");
        if (name != null) {
            model.addAttribute("memberName", name.toString());
        }

        // Initialize attributes so Thymeleaf boolean checks never see null
        model.addAttribute("hasResults", Boolean.FALSE);
        model.addAttribute("books", List.of());
        model.addAttribute("selectedSubject", "");

        return "home";
    }

    @PostMapping("/filterBySubject")
    public String filterBySubject(@RequestParam String subject, HttpSession session, Model model) {
        Object name = session.getAttribute("memberName");
        if (name != null) {
            model.addAttribute("memberName", name.toString());
        }

        if (subject == null || subject.trim().isEmpty()) {
            model.addAttribute("hasResults", false);
            model.addAttribute("books", List.of());
            model.addAttribute("selectedSubject", "");
            return "home";
        }

        List<Book> books = bookRepository.findBySubject(subject.trim(), PAGE_SIZE, 0);
        model.addAttribute("books", books);
        model.addAttribute("hasResults", !books.isEmpty());
        model.addAttribute("selectedSubject", subject.trim());
        return "home";
    }
}
