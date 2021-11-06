package com.epolsoft.brest.dao.impl;

import com.epolsoft.brest.dao.api.PersonDao;
import com.epolsoft.brest.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao {

    private JdbcTemplate jdbcTemplate;

    public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Person entity) {
        return jdbcTemplate.update("INSERT INTO person VALUES (DEFAULT, ?, ?, ?, ?)",
                entity.getFirstName(), entity.getLastName(), entity.getCountry(), entity.getCity());
    }

    @Override
    public Person read(Long id) {
        return null;
    }

    @Override
    public List<Person> readAll() {
        return null;
    }

    @Override
    public void update(Person entity) {

    }

    @Override
    public void delete(Long id) {

    }
}
