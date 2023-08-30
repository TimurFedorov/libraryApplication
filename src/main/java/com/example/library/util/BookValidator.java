package com.example.library.util;

import com.example.library.models.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class BookValidator  implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;

        if(book.getTitle().isBlank()) {
            errors.rejectValue("title", "", "Имя не должно быть пустым");
        }
        if(book.getAuthor().length() < 3 || book.getAuthor().length() > 100) {
            errors.rejectValue("author", "", "Имя должно быть от 2 до 100 символов длиной");
        }
    }
}
