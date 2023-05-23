package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.junit.jupiter.api.BeforeAll;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserControllerTest {

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void beforeEach() {
        user1 = new User("example1@gmail.com", "F@LLEN@NGEL333", "Ваня", LocalDate.of(1980,1,26));
        user2 = new User("example2@gmail.com", "DEVILDOG", "Петя", LocalDate.of(1995,8,15));
        user3 = new User("example3@gmail.com", "KNIGHT228", "Лёша", LocalDate.of(2007,11,1));
    }

    @Test
    void shouldReturnUserIfCorrectBirthDate() throws ValidationException {
        user1.setBirthday(LocalDate.of(2023,5,23));
        assertEquals(user1 , UserController.validation(user1));
    }

    @Test
    void shouldReturnEmptyUserIfIncorrectBirthDate() throws ValidationException {
        user1.setBirthday(LocalDate.of(2025,1,1));
        assertNull(UserController.validation(user1));
    }

    @Test
    void shouldReturnUserIfCorrectEmail() throws ValidationException {
        assertEquals(user1 ,UserController.validation(user1));
    }

    @Test
    void shouldReturnEmptyUserIfIncorrectEmail() throws ValidationException {
        user1.setEmail("LocalDate.of(2025,1,1)");
        assertNull(UserController.validation(user1));
        user1.setEmail("");
        assertNull(UserController.validation(user1));
    }

    @Test
    void shouldReturnUserIfCorrectLogin() throws ValidationException {
        assertEquals(user1 ,UserController.validation(user1));
    }

    @Test
    void shouldReturnEmptyUserIfIncorrectLogin() throws ValidationException {
        user1.setLogin("LocalDate. of(2025,1,1)");
        assertNull(UserController.validation(user1));
        user1.setLogin("");
        assertNull(UserController.validation(user1));
        user1.setLogin(" ");
        assertNull(UserController.validation(user1));
    }

    @Test
    void shouldReplaceNameAndReturnUserIfCorrectAll() throws ValidationException {
        user1.setName("");
        assertEquals(user1.getLogin() ,UserController.validation(user1).getName());

        user1.setName(" ");
        assertEquals(user1.getLogin() ,UserController.validation(user1).getName());
    }
}
