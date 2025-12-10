package me.andreaseriksson.bookstore;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Member member) {
        jdbcTemplate.update(
                "INSERT INTO members (fname, lname, address, city, zip, phone, email, userid, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                member.getFname(),
                member.getLname(),
                member.getAddress(),
                member.getCity(),
                member.getZip(),
                member.getPhone(),
                member.getEmail(),
                member.getUserid(),
                member.getPassword()
        );
    }
}