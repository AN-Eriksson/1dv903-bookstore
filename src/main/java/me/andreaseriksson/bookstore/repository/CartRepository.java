package me.andreaseriksson.bookstore.repository;

import me.andreaseriksson.bookstore.model.Book;
import me.andreaseriksson.bookstore.model.CartItem;
import me.andreaseriksson.bookstore.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {

    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Member member, String isbn, int qty) {
        jdbcTemplate.update(
                "INSERT INTO cart (userid, isbn, qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE qty = qty + ?",
                member.getUserid(),
                isbn,
                qty,
                qty
        );
    }

    public List<CartItem> getCartContents(Member member) {
        String sql = "SELECT b.isbn AS isbn, b.title AS title, b.author AS author, b.price AS price, b.subject AS subject, c.qty AS qty "
                + "FROM cart c "
                + "JOIN books b ON b.isbn = c.isbn "
                + "WHERE c.userid = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    String isbn = rs.getString("isbn");
                    String author = rs.getString("author");
                    String title = rs.getString("title");
                    double price = rs.getDouble("price");
                    String subject = rs.getString("subject");
                    int qty = rs.getInt("qty");

                    Book book = new Book(isbn, author, title, price, subject);
                    return new CartItem(book, qty);
                },
                member.getUserid()
        );
    }
}