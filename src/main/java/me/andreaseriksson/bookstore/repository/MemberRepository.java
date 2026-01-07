package me.andreaseriksson.bookstore.repository;

import me.andreaseriksson.bookstore.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Member member) {
        String sql = "INSERT INTO members (fname, lname, address, city, zip, phone, email, password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
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

    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT userid, fname, lname, address, city, zip, phone, email, password FROM members WHERE email = ?";
        List<Member> results = jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    Member member = new Member();
                    member.setUserid((rs.getInt("userid")));
                    member.setFname(rs.getString("fname"));
                    member.setLname(rs.getString("lname"));
                    member.setAddress(rs.getString("address"));
                    member.setCity(rs.getString("city"));
                    member.setZip(rs.getInt("zip"));
                    member.setPhone(rs.getString("phone"));
                    member.setEmail(rs.getString("email"));
                    member.setPassword(rs.getString("password"));
                    return member;
                },
                email
        );
        return results.stream().findFirst();
    }
}