package me.andreaseriksson.bookstore.service;

import me.andreaseriksson.bookstore.model.Book;
import me.andreaseriksson.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> search(String subject, String author, String title, int limit, int offset) {
        if (subject != null && !subject.trim().isEmpty()) {
            return bookRepository.findBySubject(subject.trim(), limit, offset);
        }
        if (author != null && !author.trim().isEmpty()) {
            return bookRepository.findByAuthor(author.trim(), limit, offset);
        }
        if (title != null && !title.trim().isEmpty()) {
            return bookRepository.findByTitle(title.trim(), limit, offset);
        }
        return Collections.emptyList();
    }
}
