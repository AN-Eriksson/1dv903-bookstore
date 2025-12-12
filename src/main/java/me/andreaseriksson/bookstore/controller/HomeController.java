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
import java.util.Set;

@Controller
public class HomeController {
    private final BookRepository bookRepository;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public HomeController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String subject,
                       @RequestParam(required = false) String author,
                       @RequestParam(required = false) String title,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) Integer pageSize,
                       HttpSession session, Model model) {

        if (page < 0) page = 0;

        Integer sessionPageSize = (Integer) session.getAttribute("pageSize");
        int effectivePageSize = (pageSize != null)
                ? pageSize
                : (sessionPageSize != null ? sessionPageSize : DEFAULT_PAGE_SIZE);
        session.setAttribute("pageSize", effectivePageSize);

        Object name = session.getAttribute("memberName");
        if (name != null) model.addAttribute("memberName", name.toString());

        model.addAttribute("hasResults", Boolean.FALSE);
        model.addAttribute("books", List.of());
        model.addAttribute("selectedSubject", subject == null ? "" : subject);
        model.addAttribute("selectedAuthor", author == null ? "" : author);
        model.addAttribute("selectedTitle", title == null ? "" : title);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", effectivePageSize);

        int offset = page * effectivePageSize;

        if (subject != null && !subject.trim().isEmpty()) {
            List<Book> books = bookRepository.findBySubject(subject.trim(), effectivePageSize, offset);
            model.addAttribute("books", books);
            model.addAttribute("hasResults", !books.isEmpty());
            return "home";
        }

        if (author != null && !author.trim().isEmpty()) {
            List<Book> books = bookRepository.findByAuthor(author.trim(), effectivePageSize, offset);
            model.addAttribute("books", books);
            model.addAttribute("hasResults", !books.isEmpty());
            return "home";
        }

        if (title != null && !title.trim().isEmpty()) {
            List<Book> books = bookRepository.findByTitle(title.trim(), effectivePageSize, offset);
            model.addAttribute("books", books);
            model.addAttribute("hasResults", !books.isEmpty());
            return "home";
        }

        return "home";
    }

    @PostMapping("/searchBySubject")
    public String searchBySubject(@RequestParam String subject,
                                  @RequestParam(required = false) Integer pageSize,
                                  HttpSession session,
                                  Model model) {
        if (pageSize != null) {
            session.setAttribute("pageSize", pageSize);
        }
        int effectivePageSize = (Integer) session.getAttribute("pageSize");

        Object name = session.getAttribute("memberName");
        if (name != null) model.addAttribute("memberName", name.toString());

        if (subject == null || subject.trim().isEmpty()) {
            model.addAttribute("hasResults", false);
            model.addAttribute("books", List.of());
            model.addAttribute("selectedSubject", "");
            return "home";
        }

        List<Book> books = bookRepository.findBySubject(subject.trim(), effectivePageSize, 0);
        model.addAttribute("books", books);
        model.addAttribute("hasResults", !books.isEmpty());
        model.addAttribute("selectedSubject", subject.trim());
        model.addAttribute("page", 0);
        model.addAttribute("pageSize", effectivePageSize);
        return "home";
    }

    @PostMapping("/searchByAuthor")
    public String searchByAuthor(@RequestParam String author,
                                 @RequestParam(required = false) Integer pageSize,
                                 HttpSession session,
                                 Model model) {
        if (pageSize != null) {
            session.setAttribute("pageSize", pageSize);
        }
        int effectivePageSize = (Integer) session.getAttribute("pageSize");

        Object name = session.getAttribute("memberName");
        if (name != null) model.addAttribute("memberName", name.toString());

        if (author == null || author.trim().isEmpty()) {
            model.addAttribute("hasResults", false);
            model.addAttribute("books", List.of());
            model.addAttribute("selectedAuthor", "");
            return "home";
        }

        List<Book> books = bookRepository.findByAuthor(author.trim(), effectivePageSize, 0);
        model.addAttribute("books", books);
        model.addAttribute("hasResults", !books.isEmpty());
        model.addAttribute("selectedAuthor", author.trim());
        model.addAttribute("page", 0);
        model.addAttribute("pageSize", effectivePageSize);
        return "home";
    }

    @PostMapping("/searchByTitle")
    public String searchByTitle(@RequestParam String title,
                                @RequestParam(required = false) Integer pageSize,
                                HttpSession session,
                                Model model) {
        if (pageSize != null) {
            session.setAttribute("pageSize", pageSize);
        }
        int effectivePageSize = (Integer) session.getAttribute("pageSize");

        Object name = session.getAttribute("memberName");
        if (name != null) model.addAttribute("memberName", name.toString());

        if (title == null || title.trim().isEmpty()) {
            model.addAttribute("hasResults", false);
            model.addAttribute("books", List.of());
            model.addAttribute("selectedTitle", "");
            return "home";
        }

        List<Book> books = bookRepository.findByTitle(title.trim(), effectivePageSize, 0);
        model.addAttribute("books", books);
        model.addAttribute("hasResults", !books.isEmpty());
        model.addAttribute("selectedTitle", title.trim());
        model.addAttribute("page", 0);
        model.addAttribute("pageSize", effectivePageSize);
        return "home";
    }
}
