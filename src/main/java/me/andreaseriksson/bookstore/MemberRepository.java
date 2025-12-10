package me.andreaseriksson.bookstore;

import org.springframework.dao.EmptyResultDataAccessException;
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
                "INSERT INTO members (fname, lname, address, city, zip, phone, email, password) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                member.getFname(),
                member.getLname(),
                member.getAddress(),
                member.getCity(),
                member.getZip(),
                member.getPhone(),
                member.getEmail(),
                member.getPassword()
        );
    }

    public Member findByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT userid, fname, lname, address, city, zip, phone, email, password FROM members WHERE email = ?",
                    (rs, rowNum) -> {
                        Member m = new Member();
                        m.setUserid((rs.getInt("userid")));
                        m.setFname(rs.getString("fname"));
                        m.setLname(rs.getString("lname"));
                        m.setAddress(rs.getString("address"));
                        m.setCity(rs.getString("city"));
                        m.setZip(rs.getInt("zip"));
                        m.setPhone(rs.getString("phone"));
                        m.setEmail(rs.getString("email"));
                        m.setPassword(rs.getString("password"));
                        return m;
                    },
                    email
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}