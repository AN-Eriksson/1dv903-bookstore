package me.andreaseriksson.bookstore;

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
}
