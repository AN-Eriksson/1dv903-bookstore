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
        return jdbcTemplate.query(
                "SELECT isbn, author, title, price, subject FROM books",
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
}
