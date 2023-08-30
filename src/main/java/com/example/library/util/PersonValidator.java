package com.example.library.util;

import com.example.library.models.Person;
import com.example.library.services.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
@Component
public class PersonValidator implements Validator {

    @Autowired
    private final PeopleService peopleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (peopleService.getPersonByFullName(person.getFullName()).isPresent())
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже существует");
        if(person.getFullName().isBlank()) {
            errors.rejectValue("fullName", "", "Имя не должно быть пустым");
        }
        if(person.getFullName().length() < 3 || person.getFullName().length() > 100) {
            errors.rejectValue("fullName", "", "Имя должно быть от 2 до 100 символов длиной");
        }
        if(person.getYearOfBirth() < 1900) {
            errors.rejectValue("yearOfBirth","", "Год рождения должен быть больше, чем 1900");
        }
    }
}
