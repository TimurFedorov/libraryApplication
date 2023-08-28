package com.example.library.services;

import com.example.library.models.Book;
import com.example.library.models.Person;

import java.util.List;

public interface BookService {

    List<Book> finaAll();
    Book findOne(int id);
    void save(Book book);
    void delete(int id);
    void update(int id, Book updatedBook);
    void release(int id);
    void assign(int id, Person selectedPerson);

    Person getBookOwner(int id);
}
