package com.example.library.services.impl;

import com.example.library.models.Book;
import com.example.library.models.Person;
import com.example.library.repositories.PeopleRepository;
import com.example.library.services.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private final PeopleRepository peopleRepository;

    @Override
    public List<Person> finaAll() {
        return peopleRepository.findAll();
    }

    @Override
    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Override
    public List<Book> getBooksByPersonId(int id) {
        return peopleRepository.findById(id).map(Person::getBooks).orElse(Collections.emptyList());
    }

    @Override
    public Optional<Person> getPersonByFullName(String fullName) {
        return peopleRepository.findByFullName(fullName);
    }
}
