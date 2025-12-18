package me.andreaseriksson.bookstore.controller;

import jakarta.servlet.http.HttpSession;
import me.andreaseriksson.bookstore.model.Book;
import me.andreaseriksson.bookstore.model.Member;
import me.andreaseriksson.bookstore.service.BookService;
import me.andreaseriksson.bookstore.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HomeController {
    private final BookService bookService;
    private final CartService cartService;
    private static final int DEFAULT_PAGE_SIZE = 5;

    public HomeController(BookService bookService, CartService cartService) {
        this.bookService = bookService;
        this.cartService = cartService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String subject,
                       @RequestParam(required = false) String author,
                       @RequestParam(required = false) String title,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(required = false) Integer pageSize,
                       HttpSession session,
                       Model model) {

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

        List<Book> books = bookService.search(subject, author, title, effectivePageSize, offset);
        model.addAttribute("books", books);
        model.addAttribute("hasResults", !books.isEmpty());

        return "home";
    }

    @PostMapping("/")
    public String addToCart(@RequestParam("isbn") String isbn,
                            @RequestParam("quantity") int quantity,
                            @RequestParam(required = false) String subject,
                            @RequestParam(required = false) String author,
                            @RequestParam(required = false) String title,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(required = false) Integer pageSize,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        if (quantity < 1) {
            throw new IllegalArgumentException();
        }

        Member member = (Member) session.getAttribute("member");

        cartService.save(member, isbn, quantity);
        redirectAttributes.addFlashAttribute("message", "Added to cart");

        if (subject != null && !subject.isBlank()) redirectAttributes.addAttribute("subject", subject);
        if (author != null && !author.isBlank()) redirectAttributes.addAttribute("author", author);
        if (title != null && !title.isBlank()) redirectAttributes.addAttribute("title", title);
        if (page > 0) redirectAttributes.addAttribute("page", page);
        if (pageSize != null) redirectAttributes.addAttribute("pageSize", pageSize);

        return "redirect:/";
    }
}
