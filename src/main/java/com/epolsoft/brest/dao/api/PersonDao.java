package com.epolsoft.brest.dao.api;

import com.epolsoft.brest.model.Person;

import java.util.List;

public interface PersonDao {
    int save(Person entity);
    int[] saveAll(List<Person> people);
    List<Person> readAll();
    Integer findInDb(Person person);
}
