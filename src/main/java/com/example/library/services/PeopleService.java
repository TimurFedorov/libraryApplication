package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.Person;

import java.util.List;
import java.util.Optional;

public interface PeopleService {

    List<Person> finaAll();
    Person findOne(int id);
    void save(Person person);
    void delete(int id);
    void update(int id, Person updatedPerson);
    List<Book> getBooksByPersonId(int id);
    Optional<Person> getPersonByFullName(String fullName);
}
