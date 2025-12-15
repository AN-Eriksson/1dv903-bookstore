package me.andreaseriksson.bookstore.repository;

import me.andreaseriksson.bookstore.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}