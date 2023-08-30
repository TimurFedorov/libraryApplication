package com.example.library.services.impl;

import com.example.library.models.Book;
import com.example.library.models.Person;
import com.example.library.repositories.BooksRepository;
import com.example.library.services.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Autowired
    private final BooksRepository booksRepository;

    @Override
    public List<Book> findAll(Integer page, Integer booksPerPage, boolean isSortedByYear) {
        if (page == null || booksPerPage == null) {
            return findAllWithoutPagination(isSortedByYear);
        }
        return findAllWithPagination(page, booksPerPage, isSortedByYear);
    }

    private List<Book> findAllWithoutPagination(boolean isSortedByYear) {
        if (isSortedByYear) {
            return booksRepository.findAll(Sort.by("year"));
        }
        return booksRepository.findAll();
    }

    private List<Book> findAllWithPagination(Integer page, Integer booksPerPage, boolean isSortedByYear) {
        if (isSortedByYear) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        }
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    @Override
    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Override
    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());

        booksRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                });
    }

    @Override
    @Transactional
    public void assign(int id, Person selectedPerson) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                });
    }

    @Override
    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Override
    public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleIgnoreCaseStartingWith(query);
    }
}
