package com.example.library.controllers;


import com.example.library.models.Book;
import com.example.library.models.Person;
import com.example.library.services.BookService;
import com.example.library.services.PeopleService;
import com.example.library.util.BookValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private final BookService bookService;
    @Autowired
    private final PeopleService peopleService;
    @Autowired
    private final BookValidator bookValidator;

    @GetMapping()
    public String index(Model model,
                        @RequestParam (name = "sort_by_year" , required = false) boolean isSortedByYear,
                        @RequestParam (name = "page" , required = false) Integer page,
                        @RequestParam (name = "books_per_page" , required = false) Integer booksPerPage) {
        model.addAttribute("books", bookService.findAll(page, booksPerPage, isSortedByYear));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));

        Person bookOwner = bookService.getBookOwner(id);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.finaAll());

        return "books/show";
    }

    @GetMapping("/new")
    public String getNewBookPage(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String getSearchPage () {
        return "books/search";
    }

    @PostMapping("/search")
    public String search (Model model,@RequestParam (name = "query" ) String query) {
        model.addAttribute("books", bookService.searchByTitle(query));
        return "books/search";
    }
}
