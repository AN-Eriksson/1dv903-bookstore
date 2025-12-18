package me.andreaseriksson.bookstore.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDetailsRepository {
    private final JdbcTemplate jdbcTemplate;

    public OrderDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createOrderDetails(int ono, String isbn, int qty, float amount) {
        String sql = "INSERT INTO odetails (ono, isbn, qty, amount) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, ono, isbn, qty, amount);
    }
}
