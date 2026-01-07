package me.andreaseriksson.bookstore.repository;

import me.andreaseriksson.bookstore.model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {
    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> findAll() {
        String sql = "SELECT isbn, author, title, price, subject FROM books";
        return jdbcTemplate.query(sql,
                (resultSet, rowNumber) -> new Book(
                        resultSet.getString("isbn"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getDouble("price"),
                        resultSet.getString("subject")
                )
        );
    }

    public List<Book> findBySubject(String subject, int limit, int offset) {
        String sql = "SELECT isbn, author, title, price, subject FROM books " +
                "WHERE LOWER(subject) LIKE CONCAT(LOWER(?), '%') ORDER BY title LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Book(
                        rs.getString("isbn"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getDouble("price"),
                        rs.getString("subject")
                ),
                subject, limit, offset);
    }

    public List<Book> findByAuthor(String author, int limit, int offset) {
        String sql = "SELECT isbn, author, title, price, subject FROM books " +
                "WHERE LOWER(author) LIKE CONCAT(LOWER(?), '%') ORDER BY title LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Book(
                        rs.getString("isbn"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getDouble("price"),
                        rs.getString("subject")
                ),
                author, limit, offset);
    }

    public List<Book> findByTitle(String title, int limit, int offset) {
        String sql = "SELECT isbn, author, title, price, subject FROM books " +
                "WHERE LOWER(title) LIKE CONCAT(LOWER(?), '%') ORDER BY title LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Book(
                        rs.getString("isbn"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getDouble("price"),
                        rs.getString("subject")
                ),
                title, limit, offset);
    }

    public List<String> findAllSubjects() {
        String sql = "SELECT DISTINCT subject FROM books WHERE subject IS NOT NULL ORDER BY subject";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("subject"));
    }
}
