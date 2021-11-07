package com.epolsoft.brest.dao.api;

import com.epolsoft.brest.model.Person;

import java.util.List;

public interface PersonDao {
    int save(Person entity);
    List<Person> readAll();
}
