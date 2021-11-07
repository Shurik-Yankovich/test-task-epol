package com.epolsoft.brest.dao.impl;

import com.epolsoft.brest.dao.api.PersonDao;
import com.epolsoft.brest.mapper.PersonRowMapper;
import com.epolsoft.brest.model.Person;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    private JdbcTemplate jdbcTemplate;

    public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Person person) {
        String SQL = "INSERT INTO person VALUES (DEFAULT, ?, ?, ?, ?)";
        return jdbcTemplate.update(SQL, person.getFirstName(), person.getLastName(), person.getCountry(), person.getCity());
    }

    @Override
    public int[] saveAll(List<Person> people) {
        String SQL = "INSERT INTO person VALUES (DEFAULT, ?, ?, ?, ?)";
        return jdbcTemplate.batchUpdate(SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Person person = people.get(i);
                ps.setString(1, person.getFirstName());
                ps.setString(2, person.getLastName());
                ps.setString(3, person.getCountry() );
                ps.setString(4, person.getCity() );
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
    }

    @Override
    public List<Person> readAll() {
        String SQL = "SELECT * FROM person";
        return jdbcTemplate.query(SQL, new PersonRowMapper());
    }

    @Override
    public Integer findInDb(Person person) {
        String SQL = "SELECT COUNT(*) FROM person WHERE first_name=? AND last_name=? AND country=? AND city=?";
        return jdbcTemplate.queryForObject(SQL, Integer.class, person.getFirstName(), person.getLastName(), person.getCountry(), person.getCity());
    }
}
