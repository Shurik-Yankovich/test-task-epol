package com.epolsoft.brest.model;

import java.util.Objects;

public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private String country;
    private String city;

    public Person(Integer id, String firstName, String lastName, String country, String city) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
    }

    public Person() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return  getFirstName().equals(person.getFirstName()) &&
                getLastName().equals(person.getLastName()) &&
                getCountry().equals(person.getCountry()) &&
                getCity().equals(person.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getCountry(), getCity());
    }
}
